# Cynapus - 生活管理アプリケーション

![CI/CD Pipeline](https://github.com/Tshino118/Cynapus/workflows/Cynapus%20CI/CD%20Pipeline/badge.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Android](https://img.shields.io/badge/platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/language-Kotlin-orange.svg)

## アプリケーション概要

Cynapusは、日常生活をより効率的に管理するためのAndroidアプリケーションです。ToDo機能とメモ機能を統合し、シンプルで直感的なインターフェースを提供します。

## 主な機能

### 📝 ToDo機能
- **タスク作成**: タイトルと説明付きでタスクを作成
- **完了管理**: チェックボックスでタスクの完了/未完了を切り替え
- **タスク削除**: 不要なタスクを簡単に削除
- **視覚的表示**: 完了したタスクは取り消し線で表示
- **日時表示**: 作成日時を自動記録・表示

### 📄 メモ機能
- **メモ作成**: タイトルと内容付きでメモを作成
- **メモ編集**: 既存のメモを編集（予定機能）
- **メモ削除**: 不要なメモを簡単に削除
- **日時表示**: 作成・更新日時を自動記録・表示

## 技術仕様

### アーキテクチャ
- **MVVM（Model-View-ViewModel）パターン**を採用
- **Room Database**によるローカルデータ永続化
- **LiveData**による反応型UI更新
- **ViewBinding**による型安全なビュー操作

### 開発環境
- **言語**: Kotlin
- **最小SDKバージョン**: API 24 (Android 7.0)
- **ターゲットSDKバージョン**: API 34 (Android 14)
- **ビルドツール**: Gradle 8.5

### 主要ライブラリ
- **UI**: Material Design Components
- **データベース**: Room Persistence Library
- **ライフサイクル**: Android Architecture Components
- **ナビゲーション**: ViewPager2 + TabLayout

## アプリケーション構造

```
app/
├── data/
│   ├── entity/          # データベースエンティティ
│   ├── dao/             # データアクセスオブジェクト
│   ├── database/        # データベース設定
│   └── converter/       # 型変換器
├── repository/          # データリポジトリ
├── ui/
│   ├── todo/           # ToDo画面
│   ├── memo/           # メモ画面
│   └── adapter/        # RecyclerViewアダプター
└── MainActivity.kt     # メインアクティビティ
```

## クイックスタート

### 🚀 Makefileを使用（推奨）

```bash
# 1. リポジトリをクローン
git clone https://github.com/Tshino118/Cynapus.git
cd Cynapus
git checkout cynapus-android-app/artistic-ray
cd cynapus-app

# 2. 環境チェックとセットアップ
make check-env
make setup-env

# 3. 開発サイクル（ビルド + インストール）
make dev
```

### 🐋 Dockerを使用

```bash
# 1. Docker開発環境を起動
make docker-dev

# 2. コンテナ内でビルド
make docker-shell
# コンテナ内で: make build
```

### 📱 従来の方法

```bash
# 1. Android Studioでプロジェクトを開く
# 2. 依存関係を自動ダウンロード
# 3. エミュレーターまたは実機でアプリを実行
```

## ビルドシステム

### Makefileコマンド

| カテゴリ | コマンド | 説明 |
|---------|---------|------|
| **環境管理** | `make check-env` | 開発環境確認 |
|  | `make setup-env` | 環境セットアップ |
|  | `make clean-env` | 環境クリーン |
| **ビルド** | `make build` | デバッグビルド |
|  | `make build-release` | リリースビルド |
|  | `make bundle` | AABビルド |
|  | `make build-all` | 全形式ビルド |
| **テスト** | `make test` | 単体テスト |
|  | `make lint` | Lintチェック |
|  | `make analyze` | 全コード解析 |
| **Docker** | `make docker-dev` | Docker開発環境 |
|  | `make docker-build-ci` | Docker CIビルド |
|  | `make docker-production` | Docker本番ビルド |
| **クイック** | `make dev` | 開発サイクル |
|  | `make ci` | CI/CDタスク |
|  | `make release-prep` | リリース準備 |

詳細は [BUILD.md](BUILD.md) を参照してください。

### GitHub Actions CI/CD

自動化されたビルドパイプライン：

- **コード品質チェック**: Lint、テスト、カバレッジ
- **マルチ環境ビルド**: Debug、Release、AAB
- **セキュリティスキャン**: Trivy脆弱性スキャン
- **Dockerテスト**: コンテナ環境でのビルド検証
- **自動デプロイ**: リリース時の成果物配布

## 開発ワークフロー

### 日常開発

```bash
# 最新コードを取得
git pull origin main

# 開発サイクル
make dev

# テストとコード品質チェック
make test
make lint
```

### Docker開発

```bash
# Docker環境で開発
make docker-dev
make docker-shell

# コンテナ内で
make build
make test
```

### リリース準備

```bash
# 完全なCI/CDパイプライン
make ci

# リリース準備
make release-prep

# Docker本番ビルド
make docker-production
```

## 出力成果物

### ビルド成果物

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

## 使用方法

### ToDo管理
1. **ToDo**タブを選択
2. 右下の「+」ボタンをタップ
3. タイトルと説明を入力
4. 「保存」をタップしてタスクを作成
5. チェックボックスをタップして完了状態を切り替え
6. ゴミ箱アイコンをタップしてタスクを削除

### メモ作成
1. **Memo**タブを選択
2. 右下の「+」ボタンをタップ
3. タイトルと内容を入力
4. 「保存」をタップしてメモを作成
5. ゴミ箱アイコンをタップしてメモを削除

## データベース設計

詳細なE-R図は [ER-D.md](ER-D.md) を参照してください。

### ToDoエンティティ
- `id`: 主キー（自動生成）
- `title`: タスクタイトル
- `description`: タスク説明
- `isCompleted`: 完了状態
- `createdAt`: 作成日時
- `updatedAt`: 更新日時

### Memoエンティティ
- `id`: 主キー（自動生成）
- `title`: メモタイトル
- `content`: メモ内容
- `createdAt`: 作成日時
- `updatedAt`: 更新日時

## アーキテクチャ詳細

シーケンス図は [SEQUENCE.md](SEQUENCE.md) を参照してください。

## ドキュメント

- **[BUILD.md](BUILD.md)**: ビルドシステム詳細ガイド
- **[VERSIONS.md](VERSIONS.md)**: 使用ライブラリバージョン一覧
- **[ER-D.md](ER-D.md)**: データベースE-R図
- **[SEQUENCE.md](SEQUENCE.md)**: アプリケーションフローシーケンス図

## 開発に貢献

### 環境セットアップ

```bash
# 1. フォークしてクローン
git clone https://github.com/YOUR_USERNAME/Cynapus.git
cd Cynapus
git checkout cynapus-android-app/artistic-ray
cd cynapus-app

# 2. 開発環境確認
make check-env

# 3. 依存関係インストール
make deps

# 4. テスト実行
make test
```

### プルリクエスト

1. フィーチャーブランチを作成
2. 変更を実装
3. `make ci` でローカルテスト
4. プルリクエストを作成

## トラブルシューティング

### よくある問題

```bash
# 完全クリーンからやり直し
make deep-clean
make setup-env
make deps
make build

# Docker環境をリセット
make docker-clean
make docker-build
```

### ログとデバッグ

```bash
# 詳細ログでビルド
make build GRADLE_OPTS="-Dorg.gradle.debug=true"

# Dockerログ確認
make docker-logs
```

## 今後の開発予定

- [ ] タスクの編集機能
- [ ] メモの編集機能
- [ ] カテゴリ分類機能
- [ ] 検索機能
- [ ] データのエクスポート/インポート
- [ ] ダークテーマ対応
- [ ] リマインダー通知
- [ ] ウィジェット対応
- [ ] バックアップ・同期機能

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。

## 開発者

Cynapus Development Team

## サポート

- **Issues**: [GitHub Issues](https://github.com/Tshino118/Cynapus/issues)
- **Discussions**: [GitHub Discussions](https://github.com/Tshino118/Cynapus/discussions)
- **Documentation**: [GitHub Pages](https://tshino118.github.io/Cynapus/docs/)

---

*このアプリケーションは生活の質を向上させることを目的として開発されました。*

**Made with ❤️ using [Claude Code](https://claude.ai/code)**