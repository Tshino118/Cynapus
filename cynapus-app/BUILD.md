# Cynapus - ビルドガイド

## 概要

このドキュメントでは、CynapusアプリケーションのビルドプロセスとMakefileの使用方法について説明します。

## 前提条件

### 必要なソフトウェア

| ソフトウェア | 最小バージョン | 推奨バージョン | 説明 |
|-------------|--------------|--------------|------|
| Java JDK | 8 | 17 | Android開発用Java環境 |
| Android SDK | API 24 | API 34 | Android開発キット |
| Gradle | 7.0 | 8.5 | ビルドツール |
| Make | 3.8 | 4.0+ | ビルド自動化ツール |

### 環境変数設定

```bash
# Android SDK パスを設定
export ANDROID_HOME=/path/to/android-sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
```

## Makefileの使用方法

### 基本コマンド

```bash
# ヘルプの表示
make help

# 環境チェック
make check-env

# 環境セットアップ
make setup-env
```

### 開発フロー

#### 1. 初回セットアップ

```bash
# 環境確認とセットアップ
make check-env
make setup-env

# 依存関係のインストール
make deps
```

#### 2. 開発サイクル

```bash
# 開発用クイックビルド（clean + build + install）
make dev

# 個別実行の場合
make clean       # クリーン
make build       # デバッグビルド
make install     # デバイスにインストール
```

#### 3. テスト実行

```bash
# 単体テスト
make test

# Lintチェック
make lint

# すべてのコード解析
make analyze
```

## 詳細コマンドリファレンス

### 環境管理

| コマンド | 説明 |
|---------|------|
| `make check-env` | 開発環境の確認 |
| `make setup-env` | 環境セットアップ |
| `make clean-env` | 環境クリーン |

### 依存関係管理

| コマンド | 説明 |
|---------|------|
| `make deps` | 依存関係インストール |
| `make deps-update` | 依存関係更新チェック |

### ビルド

| コマンド | 説明 |
|---------|------|
| `make build` | デバッグAPKビルド |
| `make build-release` | リリースAPKビルド |
| `make bundle` | AAB（Android App Bundle）ビルド |
| `make build-all` | すべての形式でビルド |

### テスト・品質管理

| コマンド | 説明 |
|---------|------|
| `make test` | 単体テスト実行 |
| `make test-debug` | 詳細出力付きテスト |
| `make test-coverage` | カバレッジレポート生成 |
| `make lint` | Lintチェック |
| `make analyze` | 全コード解析実行 |

### インストール・デバッグ

| コマンド | 説明 |
|---------|------|
| `make install` | デバッグAPKインストール |
| `make install-release` | リリースAPKインストール |
| `make uninstall` | アプリアンインストール |
| `make devices` | 接続デバイス一覧 |
| `make logcat` | アプリのログ表示 |

### クリーニング

| コマンド | 説明 |
|---------|------|
| `make clean` | ビルド成果物クリーン |
| `make deep-clean` | 深層クリーン（キャッシュ含む） |

### 開発補助

| コマンド | 説明 |
|---------|------|
| `make format` | コードフォーマット |
| `make docs` | ドキュメント生成 |
| `make version` | バージョン情報表示 |
| `make info` | プロジェクト情報表示 |

### クイックコマンド

| コマンド | 説明 |
|---------|------|
| `make dev` | 開発サイクル（clean + build + install） |
| `make ci` | CI/CD用タスク |
| `make release-prep` | リリース準備 |

## ビルド成果物

### 出力ディレクトリ

```
app/build/outputs/
├── apk/
│   ├── debug/          # デバッグAPK
│   └── release/        # リリースAPK
└── bundle/
    └── release/        # AAB（Google Play用）
```

### ファイル命名規則

- **デバッグAPK**: `app-debug.apk`
- **リリースAPK**: `app-release-unsigned.apk`
- **AAB**: `app-release.aab`

## 開発ワークフロー例

### 日常開発

```bash
# 1. 最新コードを取得
git pull origin main

# 2. 開発サイクル実行
make dev

# 3. テスト実行
make test

# 4. コード品質チェック
make lint
```

### リリース準備

```bash
# 1. すべてのテストとチェックを実行
make ci

# 2. リリース準備
make release-prep

# 3. バージョン確認
make version

# 4. リリースノート生成
make release-notes
```

### トラブルシューティング

```bash
# 完全クリーンからやり直し
make deep-clean
make setup-env
make deps
make build
```

## カスタマイズ

### ローカル設定

`Makefile.local`ファイルを作成して、個人設定をカスタマイズできます：

```makefile
# Makefile.local の例
ANDROID_HOME := /Users/yourname/Android/Sdk
JAVA_HOME := /usr/lib/jvm/java-17-openjdk

# カスタムタスク
.PHONY: my-build
my-build:
	@echo "My custom build process"
	make clean build test
```

### 環境変数オーバーライド

```bash
# Android SDK パスを一時的に変更
ANDROID_HOME=/custom/path/to/sdk make build

# 特定のデバイスをターゲット
ANDROID_SERIAL=emulator-5554 make install
```

## CI/CD統合

### GitHub Actions例

```yaml
name: Build and Test
on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Setup Java
      uses: actions/setup-java@v2
      with:
        java-version: '17'
    - name: Setup Android SDK
      uses: android-actions/setup-android@v2
    - name: Run CI tasks
      run: make ci
```

## エラー対処法

### よくある問題

1. **`ANDROID_HOME not set`**
   ```bash
   export ANDROID_HOME=/path/to/android-sdk
   ```

2. **`Gradle wrapper not found`**
   ```bash
   ./gradlew wrapper --gradle-version 8.5
   ```

3. **`Permission denied`**
   ```bash
   chmod +x gradlew
   ```

### ログとデバッグ

```bash
# 詳細ログでビルド
make build GRADLE_OPTS="-Dorg.gradle.debug=true"

# ビルドスキャン有効
./gradlew build --scan
```

---

*このビルドガイドは、効率的な開発ワークフローをサポートするために作成されています。質問や改善提案があれば、開発チームまでお知らせください。*