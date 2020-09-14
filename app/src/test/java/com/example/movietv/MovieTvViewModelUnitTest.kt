package com.example.movietv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movietv.callback.GetDetailMovieTvCallback
import com.example.movietv.callback.GetMovieTvCallback
import com.example.movietv.data.model.Cast
import com.example.movietv.data.model.MovieTvModel
import com.example.movietv.data.repository.MovieTvRepository
import com.example.movietv.ui.home.MovieTvViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnitRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class MovieTvViewModelUnitTest {
    private lateinit var viewModel: MovieTvViewModel

    @MockK
    private lateinit var repository: MovieTvRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var observer: Observer<List<MovieTvModel>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MovieTvViewModel(repository)
    }

    @Test
    fun movieList_fromLocalJson_shouldNotNullAndExpectedSize() {
        val fiveSizeList =
            listOf(MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel())
        val captureCallback = slot<GetMovieTvCallback>()
        every { repository.getMovie(capture(captureCallback)) } answers {
            captureCallback.captured.onSuccess(fiveSizeList)
        }
        viewModel.getMovieList()
        assertNotNull(viewModel.movieList.value)
        assertEquals(5, viewModel.movieList.value?.size)

        viewModel.movieList.observeForever(observer)
        verify { observer.onChanged(fiveSizeList) }
    }

    @Test
    fun tvShowList_fromLocalJson_shouldNotNullAndExpectedSize() {
        val fiveSizeList =
            listOf(MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel(), MovieTvModel())
        val captureCallback = slot<GetMovieTvCallback>()
        every { repository.getTvShow(capture(captureCallback)) } answers {
            captureCallback.captured.onSuccess(fiveSizeList)
        }
        viewModel.getTvShowList()
        assertNotNull(viewModel.tvShowList.value)
        Assert.assertEquals(5, viewModel.tvShowList.value?.size)

        viewModel.tvShowList.observeForever(observer)
        verify { observer.onChanged(fiveSizeList) }
    }

    @Test
    fun movieItem_fromLocalJson_shouldGiveExpectedItem() {
        val captureCallback = slot<GetDetailMovieTvCallback>()
        val expectedMovie = MovieTvModel(
            547016,
            "en",
            "The Old Guard",
            "/cjr4NWURcVN3gW5FlHeabgBHLrY.jpg",
            "/m0ObOaJBerZ3Unc74l471ar8Iiy.jpg",
            false,
            "Four undying warriors who've secretly protected humanity for centuries become targeted for their mysterious powers just as they discover a new immortal.",
            listOf(
                "Action",
                "Fantasy"
            ),
            124,
            listOf(Cast("", ""), Cast("", ""), Cast("", ""), Cast("", ""), Cast("", "")),
            "2020-07-10",
            7.3,
            1865
        )
        every { repository.getDetailMovie(547016, capture(captureCallback)) } answers {
            captureCallback.captured.onSuccess(expectedMovie)
        }
        viewModel.getMovie(547016)
        assertNotNull(viewModel.movieTv.value)
        assertEquals(expectedMovie.language, viewModel.movieTv.value?.language)
        assertEquals(expectedMovie.title, viewModel.movieTv.value?.title)
        assertEquals(expectedMovie.posterUrl, viewModel.movieTv.value?.posterUrl)
        assertEquals(expectedMovie.backdropUrl, viewModel.movieTv.value?.backdropUrl)
        assertEquals(expectedMovie.isForAdult, viewModel.movieTv.value?.isForAdult)
        assertEquals(expectedMovie.overview, viewModel.movieTv.value?.overview)
        assertEquals(expectedMovie.genre.size, viewModel.movieTv.value?.genre?.size)
        assertEquals(expectedMovie.duration, viewModel.movieTv.value?.duration)
        assertEquals(expectedMovie.castList?.size, viewModel.movieTv.value?.castList?.size)
        assertEquals(expectedMovie.releasedDate, viewModel.movieTv.value?.releasedDate)
        assertEquals(expectedMovie.rating, viewModel.movieTv.value?.rating)
        assertEquals(expectedMovie.vote, viewModel.movieTv.value?.vote)
    }

    @Test
    fun tvShowItem_fromLocalJson_shouldGiveExpectedItem() {
        val captureCallback = slot<GetDetailMovieTvCallback>()
        val expectedTvShow = MovieTvModel(
            1416,
            "en",
            "Grey's Anatomy",
            "/jnsvc7gCKocXnrTXF6p03cICTWb.jpg",
            "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
            null,
            "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
            listOf("Drama"),
            43,
            null,
            null,
            8.0,
            3390
        )
        every { repository.getDetailTvShow(1416, capture(captureCallback)) } answers {
            captureCallback.captured.onSuccess(expectedTvShow)
        }
        viewModel.getTvShow(1416)
        assertNotNull(viewModel.movieTv.value)
        assertEquals(expectedTvShow.language, viewModel.movieTv.value?.language)
        assertEquals(expectedTvShow.title, viewModel.movieTv.value?.title)
        assertEquals(expectedTvShow.posterUrl, viewModel.movieTv.value?.posterUrl)
        assertEquals(expectedTvShow.backdropUrl, viewModel.movieTv.value?.backdropUrl)
        assertEquals(expectedTvShow.isForAdult, viewModel.movieTv.value?.isForAdult)
        assertEquals(expectedTvShow.overview, viewModel.movieTv.value?.overview)
        assertEquals(expectedTvShow.genre.size, viewModel.movieTv.value?.genre?.size)
        assertEquals(expectedTvShow.duration, viewModel.movieTv.value?.duration)
        assertEquals(expectedTvShow.castList?.size, viewModel.movieTv.value?.castList?.size)
        assertEquals(expectedTvShow.releasedDate, viewModel.movieTv.value?.releasedDate)
        assertEquals(expectedTvShow.rating, viewModel.movieTv.value?.rating)
        assertEquals(expectedTvShow.vote, viewModel.movieTv.value?.vote)
    }
}