# Cynapus - Docker Environment Guide

## Docker環境の使用方法

このドキュメントでは、CynapusアプリケーションのDocker環境のセットアップと使用方法について説明します。

## 前提条件

### 必要なソフトウェア

- **Docker**: 20.10以降
- **Docker Compose**: 2.0以降 (または `docker compose` コマンド)
- **Make**: ビルド自動化用

### システム要件

- **メモリ**: 最低8GB推奨
- **ストレージ**: 20GB以上の空き容量
- **OS**: Linux、macOS、Windows (WSL2推奨)

## クイックスタート

### 1. 軽量版で開始（推奨）

```bash
# 環境確認
make docker-check

# 軽量版開発環境をビルド
make docker-build

# 開発環境を起動
make docker-dev

# コンテナにアクセス
make docker-shell
```

### 2. フル機能版の使用

```bash
# フル機能版をビルド
make docker-build-full

# フル機能版を起動
docker compose up -d cynapus-dev-full

# コンテナにアクセス
docker compose exec cynapus-dev-full bash
```

## Docker環境の種類

### 1. Slim版（推奨）- `Dockerfile.slim`

**特徴:**
- 軽量で高速
- 必要最小限のパッケージ
- 開発に必要な基本ツールのみ

**用途:**
- 日常的な開発作業
- CI/CDパイプライン
- 高速ビルド

**ビルド:**
```bash
make docker-build-slim
# または
docker build -f Dockerfile.slim -t cynapus-android:slim .
```

### 2. Full版 - `Dockerfile`

**特徴:**
- 完全な開発環境
- 追加の開発ツール
- テスト・ドキュメント生成機能

**用途:**
- 詳細な開発作業
- 包括的なテスト
- ドキュメント生成

**ビルド:**
```bash
make docker-build-full
```

## 利用可能なサービス

### 開発環境

#### Slim版開発環境
```bash
# 起動
make docker-dev

# アクセス
make docker-shell

# 停止
docker compose down cynapus-dev
```

#### Full版開発環境
```bash
# 起動
docker compose up -d cynapus-dev-full

# アクセス
docker compose exec cynapus-dev-full bash

# 停止
docker compose down cynapus-dev-full
```

### CI/CDビルド環境

```bash
# CIタスクを実行
make docker-build-ci

# または直接実行
docker compose run --rm cynapus-build
```

### 本番ビルド環境

```bash
# 本番ビルドを実行
make docker-production

# 成果物は ./build-output/ に出力
```

### テスト環境

```bash
# テスト実行
make docker-test

# または直接実行
docker compose run --rm cynapus-test
```

## 開発ワークフロー

### 日常開発（Slim版使用）

```bash
# 1. 開発環境を起動
make docker-dev

# 2. コンテナにアクセス
make docker-shell

# 3. コンテナ内でビルド
developer@container:/workspace$ make build

# 4. テスト実行
developer@container:/workspace$ make test

# 5. 作業終了時
exit
docker compose down
```

### CI/CD統合

```bash
# 完全なCI/CDパイプライン
make docker-dev-cycle

# 段階的実行
make docker-build-ci   # ビルド
make docker-test       # テスト
make docker-production # 本番ビルド
```

## ファイル共有とボリューム

### ボリュームマッピング

| ホストパス | コンテナパス | 説明 |
|-----------|-------------|------|
| `.` | `/workspace` | プロジェクトファイル |
| `gradle-cache` | `/home/developer/.gradle` | Gradleキャッシュ |
| `android-cache` | `/home/developer/.android` | Androidキャッシュ |

### 成果物の取得

```bash
# ビルド成果物
./build-output/           # 本番ビルド成果物
./docs-output/           # ドキュメント
./artifacts/             # その他の成果物
```

## ポート設定

| サービス | ホストポート | コンテナポート | 説明 |
|---------|-------------|--------------|------|
| cynapus-dev | 8080 | 8080 | 開発サーバー |
| cynapus-dev | 5037 | 5037 | ADB |
| cynapus-dev-full | 8081 | 8080 | 開発サーバー（Full版） |
| cynapus-dev-full | 5038 | 5037 | ADB（Full版） |

## トラブルシューティング

### 一般的な問題

#### 1. Docker環境のリセット

```bash
# 完全クリーンアップ
make docker-clean

# イメージ再ビルド
make docker-build-all
```

#### 2. メモリ不足

```bash
# Dockerのメモリ制限を確認
docker system df

# 未使用リソースをクリーンアップ
docker system prune -f

# ボリュームもクリーンアップ
docker system prune -a --volumes
```

#### 3. パーミッション問題

```bash
# 開発用ユーザーでコンテナを実行
docker compose exec --user developer cynapus-dev bash

# または所有権を修正
sudo chown -R $USER:$USER .
```

#### 4. ネットワーク問題

```bash
# ネットワークをリセット
docker network prune

# コンテナを再作成
docker compose down
docker compose up -d --force-recreate
```

### ログの確認

```bash
# 全コンテナのログ
make docker-logs

# 特定のサービスのログ
docker compose logs cynapus-dev

# リアルタイムログ
docker compose logs -f cynapus-dev
```

## パフォーマンス最適化

### 1. ビルドキャッシュの活用

```bash
# Docker BuildKitを有効化
export DOCKER_BUILDKIT=1

# マルチステージビルドキャッシュ
docker build --target development --cache-from cynapus-android:dev .
```

### 2. ボリュームキャッシュ

```bash
# 名前付きボリュームを使用（推奨）
# docker-compose.ymlで自動設定済み

# バインドマウントの代わりに
volumes:
  - gradle-cache:/home/developer/.gradle
```

### 3. 並列ビルド

```bash
# 並列ビルド
docker compose build --parallel

# 並列サービス起動
docker compose up -d cynapus-dev cynapus-build
```

## CI/CD統合

### GitHub Actions

```yaml
# .github/workflows/docker.yml例
- name: Build Docker image
  run: |
    make docker-build-slim
    make docker-test
```

### Jenkins

```groovy
// Jenkinsfile例
stage('Docker Build') {
    steps {
        sh 'make docker-build-ci'
    }
}
```

## 高度な使用方法

### カスタムDockerfile

```dockerfile
# Dockerfile.custom
FROM cynapus-android:slim
RUN apt-get update && apt-get install -y your-custom-tools
```

### 環境変数のカスタマイズ

```bash
# .env ファイルを作成
echo "GRADLE_OPTS=-Xmx4g" > .env
echo "ANDROID_HOME=/opt/android-sdk" >> .env

# docker compose で自動読み込み
docker compose up -d
```

### デバッグ用設定

```bash
# デバッグモードで起動
docker compose -f docker-compose.yml -f docker-compose.debug.yml up -d

# 対話モードで実行
docker compose run --rm cynapus-dev bash
```

## ベストプラクティス

1. **定期的なクリーンアップ**
   ```bash
   # 週1回程度実行
   docker system prune -f
   ```

2. **適切なリソース管理**
   - Docker Desktopのメモリ設定: 8GB以上
   - CPUコア数: 4コア以上推奨

3. **セキュリティ**
   - 本番環境では非rootユーザーを使用
   - 機密情報はSecretとして管理

4. **効率的な開発**
   - Slim版を日常使用
   - Full版は特別な作業時のみ

---

*Docker環境により、一貫した開発体験と効率的なCI/CDパイプラインを実現できます。*