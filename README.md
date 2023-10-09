# Asteroid Radar
Asteroid Radar allows you to view a list of asteroids detected by NASA, you can view all the detected asteroids in a period of time, their data (size, velocity, diameter and distance to Earth) and if they are potentially hazardous or not.

The app retrieves data from two APIs the NeoWs API for the list of asteroids and the APOD API for a picture of the day.

The application:
* downloads and parses data from the NASA NeoWs API and saves it into a database.
* includes a main screen that consists of the picture of the day and the list of detected asteroids from the database.
* includes a menu that can filter the list of asteroids by day, week or show all the saved asteroids.
* includes a detail screen that shows more information about an asteroid clicked from the main screen.
* has a worker that caches(downloads and saves) asteroids in the background once a day.
* has a worker that deletes asteroids from the previous day.
* works with multiple screen sizes and orientations and also provides talk back and push button navigation.

# Setup
A demo api key is present in the project for testing, To get additional requests from the api, you could get an api key at https://api.nasa.gov/ and replace the demo key with yours.

# Used Libraries and Technologies
[Kotlin](https://kotlinlang.org/) - Primary programming language.

[MVVM](https://developer.android.com/topic/architecture) - Structured Model-View-ViewModel architecture.

[RecyclerView](https://developer.android.com/develop/ui/views/layout/recyclerview) - Display lists.

[Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - Thread management and concurrency.

[Retrofit](https://github.com/square/retrofit) - Rest API calls.

[Room](https://developer.android.com/training/data-storage/room/) - Local database management.

[AndroidX Components](https://developer.android.com/jetpack/androidx/) - Jetpack components.

[Coil](https://coil-kt.github.io/coil/) - Loading remote images.


# Screenshots
<p align="center">
<img src="https://github.com/venfriti/asteroid-radar/assets/90982374/f95211d1-a09f-445f-b72d-955f060601ca" alt="Image description" width="230" height="500">
<img src="https://github.com/venfriti/asteroid-radar/assets/90982374/d14177fe-bbd1-4be5-889c-2cf408a559e3" alt="Image description" width="230" height="500">
<img src="https://github.com/venfriti/asteroid-radar/assets/90982374/e58554f2-d172-4034-8b9b-81ca9dd64e9f" alt="Image description" width="230" height="500">

