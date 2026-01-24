# VeryGoodMovies

A movie browsing app built for Android. It pulls data from TMDB and lets you browse popular movies or search for specific ones.

## Setup

1. Get a free API key from [The Movie Database (TMDB)](https://www.themoviedb.org/documentation/api).
2. Clone this repo.
3. In `data/di/NetworkModule.kt`, replace `API_KEY` with your actual API key and you can store it in the local properties.
4. Build and run the app on an Android device or emulator.

## What's Inside

The app follows Clean Architecture with separate modules:

- **app** - Entry point, MainActivity
- **domain** - Business logic, use cases, models
- **data** - API calls, DTOs, repository implementation
- **core** - Common utilities, that are data-agnostic
- **ui** - Screens, ViewModels, Compose components

Dependencies flow inward: `app -> ui -> data -> domain`

## Tech Used

- Jetpack Compose for UI
- Hilt for dependency injection
- Retrofit + OkHttp for networking
- Paging 3 for infinite scroll
- Coil for image loading
- Coroutines + Flow for async stuff
- Room for local caching
- Material 3 design components

## Features

- Browse popular movies with infinite scrolling
- Search movies by title
- See movie posters, ratings, and descriptions
- Material 3 design
- Error handling with retry
- Offline support for favorite movies

## Project Structure

```
domain/
  model/Movie.kt
  repository/MovieRepository.kt
  usecase/GetPopularMoviesUseCase.kt, SearchMoviesUseCase.kt

data/
  di/NetworkModule.kt, RepositoryModule.kt
  remote/api/MovieApiService.kt
  remote/dto/MovieDto.kt, MovieResponse.kt
  paging/MoviePagingSource.kt
  repository/MovieRepositoryImpl.kt

ui/
  movies/MoviesScreen.kt, MoviesViewModel.kt
  movies/components/MovieItem.kt, SearchBar.kt, etc.

core/  
    util/NetworkUtils.kt, Resource.kt
```
