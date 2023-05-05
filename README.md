![CryptoHQ app banner](/docs/images/app-banner.png "CryptoHQ app banner")

# CryptoHQ Android App

CryptoHQ is a cryptocurrency tracking Android app, built using Kotlin and Jetpack Compose. The app
follows the layered
architecture [recommended by Google](https://developer.android.com/topic/architecture).

# Architecture

The app is architected using layered architecture which provides several benefits such as
scalability, testability and separation of concerns. It allows for the separation of code into
distinct layers, which makes it easier to modify, test and maintain the application.

This app architecture has following three layers:

- **UI layer:** This layer is responsible for displaying the UI and handling user input. It uses Jetpack Compose for UI development and Android ViewModel as state holder.
- **Domain layer:** This layer contains the business logic of the app. It is responsible for fetching data from the repository and processing it before passing it to the UI layer. It is also responsible for updating the repository with any changes made by the user.
- **Data layer:** This layer is responsible for fetching data from the APIs. It provides a repository interface for the domain layer to interact with.

<p align="center">
<img alt="Architecture diagram" src="/docs/images/arch-diagram.png" />
</p>

The architecture also follows a reactive programming model with unidirectional data flow where data
flows up and events flow down.

<p align="center">
<img alt="Data flow diagram" src="/docs/images/data-flow-diagram.png" />
</p>

# Modularization

In addition, the app follows a modularized approach to make it scalable, maintainable and reduce
built times. The app has the following modules:
| Module | Responsibilities |
|--|--|
| `feature` | Each feature of the app has its own module, such as the `coin-detail` or `watchlist` feature module. These modules contain the UI, ViewModel, UiState and any other resources needed for the feature. They depend on the domain module. |
| `domain` | This module contains the business logic of the app in the form of `usecases`. It interacts with the repositories in the `data` module and defines its own domain models that are used in the app. |
| `data` | This module contains the `repository` interface and implementations *(default and fake)* for fetching data from the network and preference data sources. It depends on the network and datastore modules. |
| `network` | This module is responsible for managing all network-related functionality. It includes an interface called `NetworkDataSource` which provides an abstraction layer for making API calls and handling responses. The implementation of this interface is backed by Retrofit. |
| `datastore` | This module contains the preference-backed datastore implementation for storing app preferences. |
| `design-system` | This module contains the implementation of the Material design system used in the app. It includes various files such as `Theme`, `Color`, `Type`, and `Shape`, which collectively form the design system. |
| `common` | This module contains the common util classes and composables used throughout the app. Some examples are: `EmptyState`, `ErrorMessageWithIcon` and `Result` |

# Testing

The app has unit and UI tests for all features placed in their respective `test` and `androidTest`
directories.

To facilitate testing, data layer components (repository & datasource) are defined as interfaces.
These interfaces have test implementations defined in the `testing` module. Whenever these
components are needed in test classes, these are injected using either *Dagger Hilt* or manual
constructor injection.

For the `ViewModel` tests, `Test` repository is built by following
the [guidelines](https://developer.android.com/kotlin/flow?hl=en) provided by Google. It fully
implements the repository interface and provides some methods to manipulate the state of these
repositories.

# Dependencies

The app has following dependencies:

- [Jetpack Compose](https://developer.android.com/jetpack/compose): For UI development
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation): For app navigation
  handling
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
  and [Flows](https://developer.android.com/kotlin/flow): For asynchronous & reactive programming
- [Retrofit](https://square.github.io/retrofit/): For making network API calls
- [Material Design 2](https://m2.material.io): For app theming
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android): For
  dependency injection
- [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore): For
  storing app preferences
- [JUnit 4](https://developer.android.com/training/testing/local-tests): For unit testing
- [Turbine](https://github.com/cashapp/turbine): For Testing Kotlin coroutines and flows
- [Compose UI Test Libs](https://developer.android.com/jetpack/compose/testing): For UI testing
- [Vico](https://github.com/patrykandpatrick/vico): For building graphs and charts

# License

This app is licensed under the *Apache License (Version 2.0)*. See
the [LICENSE](https://github.com/arslanshoukat/CryptoHQ/blob/main/LICENSE) file for more
information.
