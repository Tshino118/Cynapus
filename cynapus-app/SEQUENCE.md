# Cynapus - Sequence Diagrams (シーケンス図)

## アプリケーションフロー

Cynapusアプリケーションのメイン機能について、シーケンス図で処理の流れを示します。

## 1. アプリケーション起動フロー

```mermaid
sequenceDiagram
    participant User
    participant MainActivity
    participant ViewPagerAdapter
    participant TodoFragment
    participant MemoFragment
    participant TodoViewModel
    participant MemoViewModel
    participant AppDatabase
    
    User->>MainActivity: アプリ起動
    MainActivity->>ViewPagerAdapter: ViewPager設定
    ViewPagerAdapter->>TodoFragment: フラグメント作成
    ViewPagerAdapter->>MemoFragment: フラグメント作成
    
    TodoFragment->>TodoViewModel: ViewModel初期化
    TodoViewModel->>AppDatabase: TodoDao取得
    TodoViewModel->>TodoRepository: Repository作成
    
    MemoFragment->>MemoViewModel: ViewModel初期化
    MemoViewModel->>AppDatabase: MemoDao取得
    MemoViewModel->>MemoRepository: Repository作成
    
    MainActivity->>User: UI表示完了
```

## 2. ToDo作成フロー

```mermaid
sequenceDiagram
    participant User
    participant TodoFragment
    participant AlertDialog
    participant TodoViewModel
    participant TodoRepository
    participant TodoDao
    participant AppDatabase
    participant TodoAdapter
    
    User->>TodoFragment: FABボタンタップ
    TodoFragment->>AlertDialog: 追加ダイアログ表示
    AlertDialog->>User: 入力フォーム表示
    
    User->>AlertDialog: タイトル・説明入力
    User->>AlertDialog: 保存ボタンタップ
    
    AlertDialog->>TodoFragment: 入力データ返却
    TodoFragment->>TodoViewModel: insertTodo呼び出し
    
    TodoViewModel->>TodoRepository: insertTodo
    TodoRepository->>TodoDao: insertTodo
    TodoDao->>AppDatabase: SQLite INSERT実行
    AppDatabase-->>TodoDao: 挿入完了
    
    TodoDao-->>TodoRepository: 完了通知
    TodoRepository-->>TodoViewModel: 完了通知
    
    TodoViewModel->>TodoFragment: LiveData更新通知
    TodoFragment->>TodoAdapter: リスト更新
    TodoAdapter->>User: UI更新（新ToDo表示）
```

## 3. ToDo完了切り替えフロー

```mermaid
sequenceDiagram
    participant User
    participant TodoAdapter
    participant TodoFragment
    participant TodoViewModel
    participant TodoRepository
    participant TodoDao
    participant AppDatabase
    
    User->>TodoAdapter: チェックボックスタップ
    TodoAdapter->>TodoFragment: onCompletionToggle呼び出し
    TodoFragment->>TodoViewModel: toggleTodoCompletion
    
    TodoViewModel->>TodoViewModel: isCompletedフラグ反転
    TodoViewModel->>TodoRepository: updateTodo
    TodoRepository->>TodoDao: updateTodo
    TodoDao->>AppDatabase: SQLite UPDATE実行
    AppDatabase-->>TodoDao: 更新完了
    
    TodoDao-->>TodoRepository: 完了通知
    TodoRepository-->>TodoViewModel: 完了通知
    
    TodoViewModel->>TodoFragment: LiveData更新通知
    TodoFragment->>TodoAdapter: リスト更新
    TodoAdapter->>User: UI更新（取り消し線表示/非表示）
```

## 4. ToDo削除フロー

```mermaid
sequenceDiagram
    participant User
    participant TodoAdapter
    participant TodoFragment
    participant TodoViewModel
    participant TodoRepository
    participant TodoDao
    participant AppDatabase
    
    User->>TodoAdapter: 削除ボタンタップ
    TodoAdapter->>TodoFragment: onDeleteClick呼び出し
    TodoFragment->>TodoViewModel: deleteTodo
    
    TodoViewModel->>TodoRepository: deleteTodo
    TodoRepository->>TodoDao: deleteTodo
    TodoDao->>AppDatabase: SQLite DELETE実行
    AppDatabase-->>TodoDao: 削除完了
    
    TodoDao-->>TodoRepository: 完了通知
    TodoRepository-->>TodoViewModel: 完了通知
    
    TodoViewModel->>TodoFragment: LiveData更新通知
    TodoFragment->>TodoAdapter: リスト更新
    TodoAdapter->>User: UI更新（項目削除）
```

## 5. メモ作成フロー

```mermaid
sequenceDiagram
    participant User
    participant MemoFragment
    participant AlertDialog
    participant MemoViewModel
    participant MemoRepository
    participant MemoDao
    participant AppDatabase
    participant MemoAdapter
    
    User->>MemoFragment: FABボタンタップ
    MemoFragment->>AlertDialog: 追加ダイアログ表示
    AlertDialog->>User: 入力フォーム表示
    
    User->>AlertDialog: タイトル・内容入力
    User->>AlertDialog: 保存ボタンタップ
    
    AlertDialog->>MemoFragment: 入力データ返却
    MemoFragment->>MemoViewModel: insertMemo呼び出し
    
    MemoViewModel->>MemoRepository: insertMemo
    MemoRepository->>MemoDao: insertMemo
    MemoDao->>AppDatabase: SQLite INSERT実行
    AppDatabase-->>MemoDao: 挿入完了
    
    MemoDao-->>MemoRepository: 完了通知
    MemoRepository-->>MemoViewModel: 完了通知
    
    MemoViewModel->>MemoFragment: LiveData更新通知
    MemoFragment->>MemoAdapter: リスト更新
    MemoAdapter->>User: UI更新（新メモ表示）
```

## 6. メモ削除フロー

```mermaid
sequenceDiagram
    participant User
    participant MemoAdapter
    participant MemoFragment
    participant MemoViewModel
    participant MemoRepository
    participant MemoDao
    participant AppDatabase
    
    User->>MemoAdapter: 削除ボタンタップ
    MemoAdapter->>MemoFragment: onDeleteClick呼び出し
    MemoFragment->>MemoViewModel: deleteMemo
    
    MemoViewModel->>MemoRepository: deleteMemo
    MemoRepository->>MemoDao: deleteMemo
    MemoDao->>AppDatabase: SQLite DELETE実行
    AppDatabase-->>MemoDao: 削除完了
    
    MemoDao-->>MemoRepository: 完了通知
    MemoRepository-->>MemoViewModel: 完了通知
    
    MemoViewModel->>MemoFragment: LiveData更新通知
    MemoFragment->>MemoAdapter: リスト更新
    MemoAdapter->>User: UI更新（項目削除）
```

## 7. データベース初期化フロー

```mermaid
sequenceDiagram
    participant Application
    participant AppDatabase
    participant Room
    participant SQLite
    participant TodoDao
    participant MemoDao
    
    Application->>AppDatabase: getDatabase()呼び出し
    AppDatabase->>AppDatabase: INSTANCE確認
    
    alt INSTANCEが存在しない場合
        AppDatabase->>Room: databaseBuilder()
        Room->>SQLite: データベースファイル作成
        SQLite->>Room: cynapus_database作成完了
        
        Room->>SQLite: TodoEntityテーブル作成
        Room->>SQLite: MemoEntityテーブル作成
        SQLite-->>Room: テーブル作成完了
        
        Room-->>AppDatabase: データベースインスタンス返却
        AppDatabase->>AppDatabase: INSTANCE設定
    end
    
    AppDatabase->>TodoDao: todoDao()取得
    AppDatabase->>MemoDao: memoDao()取得
    AppDatabase-->>Application: 初期化完了
```

## アーキテクチャパターン

### MVVM (Model-View-ViewModel) パターン

```mermaid
sequenceDiagram
    participant View as View Layer<br/>(Fragment/Activity)
    participant ViewModel as ViewModel Layer<br/>(TodoViewModel/MemoViewModel)
    participant Model as Model Layer<br/>(Repository/DAO/Database)
    
    View->>ViewModel: ユーザーアクション
    ViewModel->>Model: データ操作要求
    Model->>Model: ビジネスロジック実行
    Model-->>ViewModel: 結果返却
    ViewModel->>View: LiveData更新通知
    View->>View: UI更新
```

## エラーハンドリングフロー

```mermaid
sequenceDiagram
    participant User
    participant Fragment
    participant ViewModel
    participant Repository
    participant Database
    
    User->>Fragment: 操作実行
    Fragment->>ViewModel: メソッド呼び出し
    ViewModel->>Repository: データ操作
    Repository->>Database: SQL実行
    
    alt 正常終了
        Database-->>Repository: 成功
        Repository-->>ViewModel: 成功
        ViewModel->>Fragment: LiveData更新
        Fragment->>User: UI更新
    else エラー発生
        Database-->>Repository: エラー
        Repository-->>ViewModel: エラー
        ViewModel->>Fragment: エラー状態通知
        Fragment->>User: エラー表示
    end
```

---

*これらのシーケンス図は、Cynapusアプリケーションの主要な処理フローを視覚化したものです。MVVMアーキテクチャパターンとRoom Databaseの活用により、保守性の高い設計となっています。*