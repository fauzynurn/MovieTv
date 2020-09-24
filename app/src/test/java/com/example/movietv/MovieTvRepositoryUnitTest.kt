//package com.example.movietv
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.movietv.callback.GetDetailMovieTvCallback
//import com.example.movietv.callback.GetMovieTvCallback
//import com.example.movietv.data.datasource.local.LocalDataSource
//import com.example.movietv.data.model.Cast
//import com.example.movietv.data.model.MovieTvModel
//import com.example.movietv.data.repository.MovieTvRepository
//import io.mockk.MockKAnnotations
//import io.mockk.every
//import io.mockk.impl.annotations.MockK
//import io.mockk.verify
//
//import org.junit.Test
//import org.junit.runner.RunWith
//
//import org.junit.Before
//import org.junit.Rule
//import org.mockito.junit.MockitoJUnitRunner
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//
//@RunWith(MockitoJUnitRunner::class)
//class MovieTvRepositoryUnitTest {
//    private lateinit var repository : MovieTvRepository
//
//    @MockK
//    private lateinit var dataSource: LocalDataSource
//
//    @MockK(relaxed = true)
//    private lateinit var movieTvCallback : GetMovieTvCallback
//
//    @MockK(relaxed = true)
//    private lateinit var detailMovieTvCallback : GetDetailMovieTvCallback
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setUp() {
//        MockKAnnotations.init(this)
//        repository = MovieTvRepository(dataSource)
//    }
//
//    @Test
//    fun movieList_fromLocalJson_shouldNotNullAndExpectedSize() {
//        val fiveSizeList =
//            listOf(MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel())
//        every { dataSource.getMovieList() } answers { fiveSizeList }
//        repository.getMovie(movieTvCallback)
//        verify { movieTvCallback.onSuccess(fiveSizeList) }
//    }
//
//    @Test
//    fun tvShowList_fromLocalJson_shouldNotNullAndExpectedSize() {
//        val fiveSizeList =
//            listOf(MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel())
//        every { dataSource.getTvShowList() } answers { fiveSizeList }
//        repository.getTvShow(movieTvCallback)
//        verify { movieTvCallback.onSuccess(fiveSizeList) }
//    }
//
//    @Test
//    fun movieItem_fromLocalJson_shouldGiveExpectedItem() {
//        val expectedMovie = MovieTvModel(
//            547016,
//            "en",
//            "The Old Guard",
//            "/cjr4NWURcVN3gW5FlHeabgBHLrY.jpg",
//            "/m0ObOaJBerZ3Unc74l471ar8Iiy.jpg",
//            false,
//            "Four undying warriors who've secretly protected humanity for centuries become targeted for their mysterious powers just as they discover a new immortal.",
//            listOf(
//                "Action",
//                "Fantasy"
//            ),
//            124,
//            listOf(Cast("", ""), Cast("", ""), Cast("", ""), Cast("", ""), Cast("", "")),
//            "2020-07-10",
//            7.3,
//            1865
//        )
//        every { dataSource.getDetailMovie(547016) } answers { expectedMovie }
//        repository.getDetailMovie(547016,detailMovieTvCallback)
//        verify { detailMovieTvCallback.onSuccess(expectedMovie) }
//    }
//
//    @Test
//    fun tvShowItem_fromLocalJson_shouldGiveExpectedItem() {
//        val expectedTvShow = MovieTvModel(
//            1416,
//            "en",
//            "Grey's Anatomy",
//            "/jnsvc7gCKocXnrTXF6p03cICTWb.jpg",
//            "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
//            null,
//            "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
//            listOf("Drama"),
//            43,
//            null,
//            null,
//            8.0,
//            3390
//        )
//        every { dataSource.getDetailTvShow(1416) } answers { expectedTvShow }
//        repository.getDetailTvShow(1416,detailMovieTvCallback)
//        verify { detailMovieTvCallback.onSuccess(expectedTvShow) }
//    }
//}