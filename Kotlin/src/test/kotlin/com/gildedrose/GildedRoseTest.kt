package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun testCommonItem() {
        val item = Item("Common item", 3, 5)
        assertItem(item, listOf(
                ItemAssertions(elapsedDays = 1, expectedSellIn = 2, expectedQuality = 4),
                ItemAssertions(elapsedDays = 2, expectedSellIn = 1, expectedQuality = 3),
                ItemAssertions(elapsedDays = 3, expectedSellIn = 0, expectedQuality = 2),
                ItemAssertions(elapsedDays = 4, expectedSellIn = -1, expectedQuality = 0),
                ItemAssertions(elapsedDays = 5, expectedSellIn = -2, expectedQuality = 0),
        ))
    }

    @Test
    fun testConjuredItem() {
        val item = Item("Conjured item", 3, 12)
        assertItem(item, listOf(
                ItemAssertions(elapsedDays = 1, expectedSellIn = 2, expectedQuality = 10),
                ItemAssertions(elapsedDays = 2, expectedSellIn = 1, expectedQuality = 8),
                ItemAssertions(elapsedDays = 3, expectedSellIn = 0, expectedQuality = 6),
                ItemAssertions(elapsedDays = 4, expectedSellIn = -1, expectedQuality = 2),
                ItemAssertions(elapsedDays = 5, expectedSellIn = -2, expectedQuality = 0),
        ))
    }

    @Test
    fun testAgedBrie() {
        val item = Item("Aged Brie", 5, 3)
        assertItem(item, listOf(
                ItemAssertions(elapsedDays = 1, expectedSellIn = 4, expectedQuality = 4),
                ItemAssertions(elapsedDays = 2, expectedSellIn = 3, expectedQuality = 5),
                ItemAssertions(elapsedDays = 3, expectedSellIn = 2, expectedQuality = 6),
                ItemAssertions(elapsedDays = 4, expectedSellIn = 1, expectedQuality = 7),
                ItemAssertions(elapsedDays = 5, expectedSellIn = 0, expectedQuality = 8),
                ItemAssertions(elapsedDays = 6, expectedSellIn = -1, expectedQuality = 10),
                ItemAssertions(elapsedDays = 25, expectedSellIn = -20, expectedQuality = 48),
                ItemAssertions(elapsedDays = 26, expectedSellIn = -21, expectedQuality = 50),
                ItemAssertions(elapsedDays = 27, expectedSellIn = -22, expectedQuality = 50)
        ))
    }

    @Test
    fun testBackstagePasses() {
        val item = Item("Backstage passes to a TAFKAL80ETC concert", 11, 3)
        assertItem(item, listOf(
                ItemAssertions(elapsedDays = 1, expectedSellIn = 10, expectedQuality = 4),
                ItemAssertions(elapsedDays = 2, expectedSellIn = 9, expectedQuality = 6),
                ItemAssertions(elapsedDays = 6, expectedSellIn = 5, expectedQuality = 14),
                ItemAssertions(elapsedDays = 7, expectedSellIn = 4, expectedQuality = 17),
                ItemAssertions(elapsedDays = 11, expectedSellIn = 0, expectedQuality = 29),
                ItemAssertions(elapsedDays = 12, expectedSellIn = -1, expectedQuality = 0),
                ItemAssertions(elapsedDays = 13, expectedSellIn = -2, expectedQuality = 0)
        ))
    }

    @Test
    fun testSulfuras() {
        val item = Item("Sulfuras, Hand of Ragnaros", 5, 80)
        assertItem(item, listOf(
                ItemAssertions(elapsedDays = 1, expectedSellIn = 5, expectedQuality = 80),
                ItemAssertions(elapsedDays = 3, expectedSellIn = 5, expectedQuality = 80),
                ItemAssertions(elapsedDays = 5, expectedSellIn = 5, expectedQuality = 80),
        ))
    }

    /**
     * Assert that an Item satisfies all the given ItemAssertions. An ItemAssertion specifies the sellIn and
     * quality an item is expected to have after a number of elapsed days.
     */
    private fun assertItem(item: Item, itemAssertions: List<ItemAssertions>) {
        val items = arrayOf(item)
        val app = GildedRose(items)
        val daysToAssertions = itemAssertions.associateBy { it.elapsedDays }
        val lastDay = daysToAssertions.keys.maxOrNull()
                ?: throw IllegalArgumentException("daysToQuality is empty")
        (1..lastDay).forEach { elapsedDays ->
            app.updateQuality()
            val assertions = daysToAssertions[elapsedDays]
            if (assertions != null) {
                assertEquals(assertions.expectedSellIn, item.sellIn,
                        "SellIn after $elapsedDays days is incorrect")
                assertEquals(assertions.expectedQuality, item.quality,
                        "Quality after $elapsedDays days is incorrect")
            }
        }
    }

    class ItemAssertions(val elapsedDays: Int, val expectedSellIn: Int, val expectedQuality: Int)
}