# Cynapus - 使用ライブラリバージョン一覧

## プラットフォーム・言語

| 項目 | バージョン | 説明 |
|------|-----------|------|
| Kotlin | 1.9.0 | メイン開発言語 |
| Android Gradle Plugin | 8.1.0 | Androidビルドツール |
| Gradle | 8.5 | ビルド自動化ツール |
| Compile SDK | 34 | コンパイル対象SDK |
| Target SDK | 34 | ターゲットSDK（Android 14） |
| Min SDK | 24 | 最小サポートSDK（Android 7.0） |

## Androidコアライブラリ

| ライブラリ | バージョン | 説明 |
|-----------|-----------|------|
| androidx.core:core-ktx | 1.12.0 | Android KTX Core extensions |
| androidx.appcompat:appcompat | 1.6.1 | Android AppCompat support |
| androidx.constraintlayout:constraintlayout | 2.1.4 | Constraint Layout |
| androidx.recyclerview:recyclerview | 1.3.2 | RecyclerView |
| androidx.fragment:fragment-ktx | 1.6.2 | Fragment KTX extensions |

## UI・マテリアルデザイン

| ライブラリ | バージョン | 説明 |
|-----------|-----------|------|
| com.google.android.material:material | 1.10.0 | Material Design Components |
| androidx.viewpager2:viewpager2 | 1.0.0 | ViewPager2（タブ切り替え） |

## データベース（Room）

| ライブラリ | バージョン | 説明 |
|-----------|-----------|------|
| androidx.room:room-runtime | 2.6.0 | Room Database runtime |
| androidx.room:room-ktx | 2.6.0 | Room Kotlin extensions |
| androidx.room:room-compiler | 2.6.0 | Room annotation processor |

## アーキテクチャコンポーネント

| ライブラリ | バージョン | 説明 |
|-----------|-----------|------|
| androidx.lifecycle:lifecycle-viewmodel-ktx | 2.7.0 | ViewModel Kotlin extensions |
| androidx.lifecycle:lifecycle-livedata-ktx | 2.7.0 | LiveData Kotlin extensions |

## ナビゲーション

| ライブラリ | バージョン | 説明 |
|-----------|-----------|------|
| androidx.navigation:navigation-fragment-ktx | 2.7.5 | Navigation Fragment KTX |
| androidx.navigation:navigation-ui-ktx | 2.7.5 | Navigation UI KTX |

## テスト

| ライブラリ | バージョン | 説明 |
|-----------|-----------|------|
| junit:junit | 4.13.2 | JUnit testing framework |
| androidx.test.ext:junit | 1.1.5 | AndroidX Test JUnit extensions |
| androidx.test.espresso:espresso-core | 3.5.1 | Espresso UI testing |

## 開発ツール・プラグイン

| ツール | バージョン | 説明 |
|--------|-----------|------|
| kotlin-kapt | 1.9.0 | Kotlin Annotation Processing Tool |
| ViewBinding | - | ビルドイン機能（型安全なビューバインディング） |

## Java互換性

| 項目 | バージョン | 説明 |
|------|-----------|------|
| Java Source Compatibility | 1.8 | Javaソース互換性 |
| Java Target Compatibility | 1.8 | Javaターゲット互換性 |
| Kotlin JVM Target | 1.8 | Kotlin JVMターゲット |

## 更新履歴

### Version 1.0.0 (2024-06-16)
- 初期リリース
- ToDo機能実装
- メモ機能実装
- Material Design適用
- Room Database統合

---

*このドキュメントは開発時点でのライブラリバージョンを記録しています。最新の依存関係については `app/build.gradle` ファイルを参照してください。*