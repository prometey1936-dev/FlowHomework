package otus.homework.flowcats

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        try {
            while (true) {
                val latestNews = catsService.getCatFact()
                emit(latestNews)
                delay(refreshIntervalMs)
            }
        } catch (e: CancellationException) {
            throw e
        }
    }
}