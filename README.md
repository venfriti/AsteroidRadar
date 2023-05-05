# Asteroid Radar
Asteroid Radar allows you to view a list of asteroids detected by NASA, you can view all the detected asteroids in a period of time, their data (size, velocity, diameter and distance to Earth) and if they are potentially hazardous or not.

The app retrieves data from two APIs the NeoWs API for the list of asteroids and the APOD API for a picture of the day.

The application:
* downloads and parses data from the NASA NeoWs API and saves it into a database.
* includes a main screen that consists of the picture of the day and the list of detected asteroids from the database.
* includes a menu that can filter the list of asteroids by day, week or show all the saved asteroids.
* includes a detail screen that shows more information about an asteroid clicked from the main screen.
* has a worker that caches(downloads and saves) asteroids in the background once a day.
* works with multiple screen sizes and orientations and also provides talk back and push button navigation.

# Setup
A demo api key is present in the project, To get additional requests from the api,you can get an api key at https://api.nasa.gov/ and replace the demo key with yours

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
<img src="https://user-images.githubusercontent.com/90982374/234612581-4df41c1a-eda8-4808-b628-3eaf3fb15b78.png" alt="Image description" width="230" height="500">
<img src="https://user-images.githubusercontent.com/90982374/234612682-45ae47c0-c33d-49c9-bde2-63d373e9c110.png" alt="Image description" width="230" height="500">
<img src="https://user-images.githubusercontent.com/90982374/234612752-67ce22ea-43a6-4cac-9743-ac64e6315f22.png" alt="Image description" width="230" height="500">
</p>
