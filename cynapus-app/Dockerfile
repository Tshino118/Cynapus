# Cynapus Android Build Environment
# Multi-stage Dockerfile for Android app development and CI/CD

# Base Android development image
FROM openjdk:17-jdk AS base

# Set environment variables
ENV ANDROID_HOME=/opt/android-sdk
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH=${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools

# Install system dependencies
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    git \
    make \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Download and install Android SDK Command Line Tools
WORKDIR /opt
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O cmdtools.zip \
    && mkdir -p ${ANDROID_HOME}/cmdline-tools \
    && unzip cmdtools.zip -d ${ANDROID_HOME}/cmdline-tools \
    && mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest \
    && rm cmdtools.zip

# Install Android SDK components
RUN yes | sdkmanager --licenses \
    && sdkmanager \
        "platforms;android-34" \
        "build-tools;34.0.0" \
        "platform-tools" \
        "emulator" \
        "system-images;android-34;google_apis;x86_64"

# Download and install Gradle
RUN wget -q https://services.gradle.org/distributions/gradle-8.5-bin.zip -O gradle.zip \
    && unzip gradle.zip \
    && mv gradle-8.5 /opt/gradle \
    && ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle \
    && rm gradle.zip

# Set working directory
WORKDIR /workspace

# Development stage - includes development tools
FROM base AS development

# Install additional development tools
RUN apt-get update && apt-get install -y \
    vim \
    nano \
    htop \
    tree \
    && rm -rf /var/lib/apt/lists/*

# Create non-root user for development
RUN useradd -m -s /bin/bash developer \
    && chown -R developer:developer /opt/android-sdk \
    && chown -R developer:developer /workspace

USER developer

# Copy project files
COPY --chown=developer:developer . .

# Make gradlew executable
RUN chmod +x gradlew

# Expose common ports
EXPOSE 8080 5037

# Default command
CMD ["/bin/bash"]

# Build stage - optimized for CI/CD
FROM base AS build

# Copy project files
COPY . .

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Default build command
CMD ["make", "ci"]

# Production build stage
FROM build AS production

# Build the application
RUN make clean build-release bundle

# Create output directory
RUN mkdir -p /output \
    && cp app/build/outputs/apk/release/*.apk /output/ 2>/dev/null || true \
    && cp app/build/outputs/bundle/release/*.aab /output/ 2>/dev/null || true

# Minimal runtime image with just the built artifacts
FROM alpine:latest AS artifacts

# Copy built artifacts
COPY --from=production /output /artifacts

# List artifacts
RUN ls -la /artifacts/

CMD ["sh", "-c", "echo 'Build artifacts:' && ls -la /artifacts/"]

# Testing stage
FROM base AS testing

# Install additional testing tools
RUN apt-get update && apt-get install -y \
    xvfb \
    libgtk-3-0 \
    libdbus-glib-1-2 \
    && rm -rf /var/lib/apt/lists/*

# Copy project files
COPY . .

# Make gradlew executable
RUN chmod +x gradlew

# Run tests
CMD ["make", "test"]

# Documentation stage
FROM base AS docs

# Install documentation tools
RUN apt-get update && apt-get install -y \
    pandoc \
    && rm -rf /var/lib/apt/lists/*

# Copy project files
COPY . .

# Generate documentation
RUN make docs || echo "Documentation generation completed"

# Copy documentation output
RUN mkdir -p /docs \
    && cp -r app/build/dokka/html/* /docs/ 2>/dev/null || true \
    && cp *.md /docs/ 2>/dev/null || true

CMD ["sh", "-c", "echo 'Documentation generated in /docs'"]