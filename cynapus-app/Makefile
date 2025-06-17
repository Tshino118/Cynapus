# Cynapus Android App - Makefile
# Author: Cynapus Development Team
# Description: Build automation and environment setup for Cynapus Android application

# Variables
PROJECT_NAME := Cynapus
APP_ID := com.cynapus.app
BUILD_DIR := build
APK_DIR := app/build/outputs/apk
AAB_DIR := app/build/outputs/bundle
GRADLE_WRAPPER := ./gradlew

# Android SDK Configuration
ANDROID_HOME ?= /opt/android-sdk
SDK_VERSION := 34
MIN_SDK_VERSION := 24

# Colors for output
GREEN := \033[0;32m
YELLOW := \033[1;33m
RED := \033[0;31m
NC := \033[0m # No Color

# Default target
.DEFAULT_GOAL := help

##@ General Commands

.PHONY: help
help: ## Display this help message
	@echo "$(GREEN)$(PROJECT_NAME) Android App - Build Automation$(NC)"
	@echo ""
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

##@ Environment Setup

.PHONY: check-env
check-env: ## Check development environment
	@echo "$(YELLOW)Checking development environment...$(NC)"
	@command -v java >/dev/null 2>&1 || { echo "$(RED)Java not found. Please install JDK 8 or higher.$(NC)"; exit 1; }
	@echo "$(GREEN)✓ Java found:$(NC) $$(java -version 2>&1 | head -n 1)"
	@test -f $(GRADLE_WRAPPER) || { echo "$(RED)Gradle wrapper not found.$(NC)"; exit 1; }
	@echo "$(GREEN)✓ Gradle wrapper found$(NC)"
	@test -n "$(ANDROID_HOME)" || { echo "$(RED)ANDROID_HOME not set.$(NC)"; exit 1; }
	@echo "$(GREEN)✓ ANDROID_HOME:$(NC) $(ANDROID_HOME)"
	@test -d "$(ANDROID_HOME)" || { echo "$(RED)Android SDK directory not found.$(NC)"; exit 1; }
	@echo "$(GREEN)✓ Android SDK directory found$(NC)"
	@echo "$(GREEN)Environment check completed successfully!$(NC)"

.PHONY: setup-env
setup-env: ## Setup development environment
	@echo "$(YELLOW)Setting up development environment...$(NC)"
	@chmod +x $(GRADLE_WRAPPER)
	@echo "$(GREEN)✓ Gradle wrapper permissions set$(NC)"
	@$(GRADLE_WRAPPER) --version
	@echo "$(GREEN)Environment setup completed!$(NC)"

.PHONY: clean-env
clean-env: ## Clean development environment
	@echo "$(YELLOW)Cleaning development environment...$(NC)"
	@rm -rf $(BUILD_DIR)
	@rm -rf .gradle
	@rm -rf app/build
	@rm -rf */build
	@echo "$(GREEN)Environment cleaned!$(NC)"

##@ Dependencies

.PHONY: deps
deps: check-env ## Download and install dependencies
	@echo "$(YELLOW)Installing dependencies...$(NC)"
	@$(GRADLE_WRAPPER) dependencies
	@echo "$(GREEN)Dependencies installed!$(NC)"

.PHONY: deps-update
deps-update: ## Update dependencies to latest versions
	@echo "$(YELLOW)Updating dependencies...$(NC)"
	@$(GRADLE_WRAPPER) dependencyUpdates
	@echo "$(GREEN)Dependencies update check completed!$(NC)"

##@ Build Commands

.PHONY: build
build: check-env ## Build debug APK
	@echo "$(YELLOW)Building debug APK...$(NC)"
	@$(GRADLE_WRAPPER) assembleDebug
	@echo "$(GREEN)Debug APK built successfully!$(NC)"
	@echo "$(GREEN)APK location:$(NC) $(APK_DIR)/debug/"

.PHONY: build-release
build-release: check-env ## Build release APK (unsigned)
	@echo "$(YELLOW)Building release APK...$(NC)"
	@$(GRADLE_WRAPPER) assembleRelease
	@echo "$(GREEN)Release APK built successfully!$(NC)"
	@echo "$(GREEN)APK location:$(NC) $(APK_DIR)/release/"

.PHONY: bundle
bundle: check-env ## Build AAB (Android App Bundle) for Google Play
	@echo "$(YELLOW)Building Android App Bundle...$(NC)"
	@$(GRADLE_WRAPPER) bundleRelease
	@echo "$(GREEN)AAB built successfully!$(NC)"
	@echo "$(GREEN)AAB location:$(NC) $(AAB_DIR)/release/"

.PHONY: build-all
build-all: clean build build-release bundle ## Build all variants (debug, release, AAB)
	@echo "$(GREEN)All build variants completed!$(NC)"

##@ Testing

.PHONY: test
test: check-env ## Run unit tests
	@echo "$(YELLOW)Running unit tests...$(NC)"
	@$(GRADLE_WRAPPER) test
	@echo "$(GREEN)Unit tests completed!$(NC)"

.PHONY: test-debug
test-debug: check-env ## Run debug unit tests with detailed output
	@echo "$(YELLOW)Running debug unit tests...$(NC)"
	@$(GRADLE_WRAPPER) testDebugUnitTest --info
	@echo "$(GREEN)Debug unit tests completed!$(NC)"

.PHONY: test-coverage
test-coverage: check-env ## Run tests with coverage report
	@echo "$(YELLOW)Running tests with coverage...$(NC)"
	@$(GRADLE_WRAPPER) testDebugUnitTestCoverage
	@echo "$(GREEN)Test coverage report generated!$(NC)"
	@echo "$(GREEN)Report location:$(NC) app/build/reports/coverage/"

.PHONY: lint
lint: check-env ## Run lint checks
	@echo "$(YELLOW)Running lint checks...$(NC)"
	@$(GRADLE_WRAPPER) lintDebug
	@echo "$(GREEN)Lint checks completed!$(NC)"
	@echo "$(GREEN)Report location:$(NC) app/build/reports/lint-results-debug.html"

.PHONY: detekt
detekt: check-env ## Run Kotlin static analysis (if configured)
	@echo "$(YELLOW)Running Kotlin static analysis...$(NC)"
	@$(GRADLE_WRAPPER) detekt || echo "$(YELLOW)Detekt not configured, skipping...$(NC)"

##@ Installation & Debugging

.PHONY: install
install: build ## Install debug APK to connected device
	@echo "$(YELLOW)Installing debug APK...$(NC)"
	@$(GRADLE_WRAPPER) installDebug
	@echo "$(GREEN)Debug APK installed successfully!$(NC)"

.PHONY: install-release
install-release: build-release ## Install release APK to connected device
	@echo "$(YELLOW)Installing release APK...$(NC)"
	@$(GRADLE_WRAPPER) installRelease
	@echo "$(GREEN)Release APK installed successfully!$(NC)"

.PHONY: uninstall
uninstall: ## Uninstall app from connected device
	@echo "$(YELLOW)Uninstalling app...$(NC)"
	@adb uninstall $(APP_ID) || echo "$(YELLOW)App not installed or device not connected$(NC)"
	@echo "$(GREEN)App uninstalled!$(NC)"

.PHONY: devices
devices: ## List connected Android devices
	@echo "$(YELLOW)Connected Android devices:$(NC)"
	@adb devices

.PHONY: logcat
logcat: ## Show logcat for the app
	@echo "$(YELLOW)Showing logcat for $(APP_ID)...$(NC)"
	@adb logcat | grep $(APP_ID)

##@ Cleaning

.PHONY: clean
clean: ## Clean build artifacts
	@echo "$(YELLOW)Cleaning build artifacts...$(NC)"
	@$(GRADLE_WRAPPER) clean
	@echo "$(GREEN)Build artifacts cleaned!$(NC)"

.PHONY: deep-clean
deep-clean: clean clean-env ## Deep clean (build artifacts + caches)
	@echo "$(YELLOW)Performing deep clean...$(NC)"
	@rm -rf ~/.gradle/caches/
	@rm -rf ~/.android/build-cache/
	@echo "$(GREEN)Deep clean completed!$(NC)"

##@ Code Quality & Analysis

.PHONY: format
format: ## Format code using ktlint (if configured)
	@echo "$(YELLOW)Formatting code...$(NC)"
	@$(GRADLE_WRAPPER) ktlintFormat || echo "$(YELLOW)ktlint not configured, skipping...$(NC)"

.PHONY: check-format
check-format: ## Check code formatting
	@echo "$(YELLOW)Checking code formatting...$(NC)"
	@$(GRADLE_WRAPPER) ktlintCheck || echo "$(YELLOW)ktlint not configured, skipping...$(NC)"

.PHONY: analyze
analyze: lint detekt test-coverage ## Run all code analysis tools
	@echo "$(GREEN)Code analysis completed!$(NC)"

##@ Documentation

.PHONY: docs
docs: ## Generate documentation
	@echo "$(YELLOW)Generating documentation...$(NC)"
	@$(GRADLE_WRAPPER) dokkaHtml || echo "$(YELLOW)Dokka not configured, skipping...$(NC)"
	@echo "$(GREEN)Documentation generated!$(NC)"

.PHONY: docs-open
docs-open: docs ## Generate and open documentation
	@echo "$(YELLOW)Opening documentation...$(NC)"
	@open app/build/dokka/html/index.html 2>/dev/null || \
	 xdg-open app/build/dokka/html/index.html 2>/dev/null || \
	 echo "$(YELLOW)Please open app/build/dokka/html/index.html manually$(NC)"

##@ Release Management

.PHONY: version
version: ## Show current version information
	@echo "$(GREEN)Project:$(NC) $(PROJECT_NAME)"
	@echo "$(GREEN)App ID:$(NC) $(APP_ID)"
	@echo "$(GREEN)Target SDK:$(NC) $(SDK_VERSION)"
	@echo "$(GREEN)Min SDK:$(NC) $(MIN_SDK_VERSION)"
	@$(GRADLE_WRAPPER) -q printVersionName || echo "$(YELLOW)Version task not configured$(NC)"

.PHONY: release-notes
release-notes: ## Generate release notes template
	@echo "$(YELLOW)Generating release notes template...$(NC)"
	@echo "# Release Notes - v$(shell date +%Y.%m.%d)" > RELEASE_NOTES.md
	@echo "" >> RELEASE_NOTES.md
	@echo "## New Features" >> RELEASE_NOTES.md
	@echo "- " >> RELEASE_NOTES.md
	@echo "" >> RELEASE_NOTES.md
	@echo "## Bug Fixes" >> RELEASE_NOTES.md
	@echo "- " >> RELEASE_NOTES.md
	@echo "" >> RELEASE_NOTES.md
	@echo "## Technical Changes" >> RELEASE_NOTES.md
	@echo "- " >> RELEASE_NOTES.md
	@echo "$(GREEN)Release notes template created: RELEASE_NOTES.md$(NC)"

##@ Quick Commands

.PHONY: dev
dev: clean build install ## Quick development cycle (clean + build + install)
	@echo "$(GREEN)Development cycle completed!$(NC)"

.PHONY: ci
ci: check-env deps lint test build ## Continuous Integration tasks
	@echo "$(GREEN)CI pipeline completed successfully!$(NC)"

.PHONY: release-prep
release-prep: clean deps lint test build-release bundle ## Prepare for release
	@echo "$(GREEN)Release preparation completed!$(NC)"
	@echo "$(YELLOW)Don't forget to:$(NC)"
	@echo "  1. Update version numbers"
	@echo "  2. Update CHANGELOG.md"
	@echo "  3. Create signed APK/AAB for distribution"
	@echo "  4. Test on multiple devices"

##@ Information

.PHONY: info
info: ## Show project information
	@echo "$(GREEN)=== $(PROJECT_NAME) Project Information ===$(NC)"
	@echo "$(GREEN)App ID:$(NC) $(APP_ID)"
	@echo "$(GREEN)Target SDK:$(NC) $(SDK_VERSION)"
	@echo "$(GREEN)Min SDK:$(NC) $(MIN_SDK_VERSION)"
	@echo "$(GREEN)Build Directory:$(NC) $(BUILD_DIR)"
	@echo "$(GREEN)APK Output:$(NC) $(APK_DIR)"
	@echo "$(GREEN)AAB Output:$(NC) $(AAB_DIR)"
	@echo ""
	@echo "$(GREEN)=== Environment Information ===$(NC)"
	@echo "$(GREEN)Android Home:$(NC) $(ANDROID_HOME)"
	@echo "$(GREEN)Java Version:$(NC) $$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)"
	@echo "$(GREEN)Gradle Version:$(NC) $$($(GRADLE_WRAPPER) --version | grep Gradle | cut -d' ' -f2)"

.PHONY: gradle-tasks
gradle-tasks: ## Show available Gradle tasks
	@echo "$(YELLOW)Available Gradle tasks:$(NC)"
	@$(GRADLE_WRAPPER) tasks

# File targets
gradle/wrapper/gradle-wrapper.properties:
	@echo "$(RED)Gradle wrapper not properly configured$(NC)"
	@exit 1

# Ensure gradle wrapper exists and is executable
$(GRADLE_WRAPPER): gradle/wrapper/gradle-wrapper.properties
	@chmod +x $(GRADLE_WRAPPER)

# Include local makefile if it exists
-include Makefile.local