# 生活管理アプリ開発ルール・仕様書

## 開発方針

### アーキテクチャ原則
- **モジュール化**: 各機能は独立したモジュールとして設計
- **疎結合**: モジュール間の依存関係を最小限に抑制
- **プラットフォーム非依存**: Web/iOS/Android対応可能な設計
- **拡張性**: 新機能追加時の影響範囲を限定

### コード規約
- **命名規則**: 
  - 関数・変数: camelCase (JavaScript/TypeScript)
  - コンポーネント: PascalCase
  - 定数: UPPER_SNAKE_CASE
- **ファイル構成**:
  ```
  src/
  ├── modules/           # 各機能モジュール
  │   ├── tasks/
  │   ├── schedule/
  │   ├── health/
  │   ├── finance/
  │   ├── goals/
  │   └── info/
  ├── shared/            # 共通コンポーネント・ユーティリティ
  ├── core/              # 認証・データ管理等の基盤機能
  └── ui/                # UIコンポーネント
  ```

## 機能仕様詳細

### ユーザー管理
- **認証方式**: Email + パスワード、OAuth（Google, Apple）
- **データ同期**: クラウドベース、リアルタイム同期
- **プライバシー**: ローカル暗号化、GDPR準拠

### ダッシュボード
- **表示優先度**: 
  1. 緊急タスク（期限24時間以内）
  2. 今日の予定
  3. 健康記録の入力忘れ
  4. 目標進捗状況
- **カスタマイズ**: ドラッグ&ドロップによるウィジェット配置

### 通知システム
- **優先度レベル**: Critical > High > Medium > Low
- **配信タイミング**: 即座、指定時刻、リマインダー
- **チャネル**: プッシュ通知、アプリ内通知、メール（オプション）

## データ設計

### 共通データ構造
```typescript
interface BaseEntity {
  id: string;
  userId: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt?: Date;
}

interface ShareableEntity extends BaseEntity {
  shareSettings: {
    isShared: boolean;
    sharedWith: string[];
    permissions: 'read' | 'write';
  };
}
```

### モジュール別データモデル

#### タスク管理
```typescript
interface Task extends ShareableEntity {
  title: string;
  description?: string;
  dueDate?: Date;
  priority: 'low' | 'medium' | 'high';
  status: 'pending' | 'in_progress' | 'completed';
  tags: string[];
  subtasks: SubTask[];
  recurrence?: RecurrencePattern;
}
```

#### 健康管理
```typescript
interface HealthRecord extends BaseEntity {
  type: 'meal' | 'exercise' | 'sleep' | 'vital' | 'medication';
  data: Record<string, any>;
  recordedAt: Date;
}
```

## UI/UX ガイドライン

### デザインシステム
- **カラーパレット**: プライマリ（青系）、セカンダリ（緑系）、アクセント（オレンジ系）
- **タイポグラフィ**: システムフォント優先、多言語対応
- **アイコン**: 一貫性のあるアイコンセット使用

### アクセシビリティ
- **WCAG 2.1 AA準拠**
- **キーボードナビゲーション対応**
- **スクリーンリーダー対応**
- **カラーコントラスト比4.5:1以上**

## セキュリティ要件

### データ保護
- **暗号化**: AES-256（保存時）、TLS 1.3（通信時）
- **認証**: JWT + リフレッシュトークン
- **アクセス制御**: RBAC（Role-Based Access Control）

### プライバシー
- **データ最小化**: 必要最小限のデータのみ収集
- **同意管理**: 明示的な同意取得
- **削除権**: ユーザーデータ完全削除機能

## パフォーマンス要件

### 応答時間
- **画面遷移**: 300ms以内
- **データ読み込み**: 1秒以内
- **検索**: 500ms以内

### リソース使用量
- **メモリ使用量**: 100MB以下（モバイル）
- **ストレージ**: 効率的なキャッシュ戦略
- **バッテリー**: バックグラウンド処理最適化

## テスト戦略

### テストレベル
1. **単体テスト**: 各モジュール・関数レベル
2. **統合テスト**: モジュール間連携
3. **E2Eテスト**: ユーザーシナリオベース
4. **パフォーマンステスト**: 負荷・ストレステスト

### テストカバレッジ
- **コードカバレッジ**: 80%以上
- **分岐カバレッジ**: 75%以上
- **クリティカルパス**: 100%

## 開発プロセス

### ブランチ戦略
- **main**: 本番環境用
- **develop**: 開発統合用
- **feature/**: 機能開発用
- **hotfix/**: 緊急修正用

### リリース管理
- **バージョニング**: セマンティックバージョニング
- **デプロイメント**: CI/CD パイプライン
- **ロールバック**: 自動ロールバック機能

## 運用・保守

### モニタリング
- **エラー追跡**: Sentry等のエラートラッキング
- **パフォーマンス監視**: APM（Application Performance Monitoring）
- **ユーザー行動分析**: プライバシー配慮した分析

### バックアップ・復旧
- **自動バックアップ**: 日次、週次、月次
- **災害復旧**: RPO 1時間、RTO 4時間
- **データ整合性**: 定期的な整合性チェック