package com.gildedrose

class GildedRose(private var items: Array<Item>) {
    /**
     * Invoked at the end of each day. Lowers the sellIn property of all items and updates the quality as needed.
     */
    fun updateQuality() {
        items.forEach { item ->
            when (ItemType.getType(item)) {
                // "Sulfuras" never decreases in Quality and never has to be sold.
                ItemType.Sulfuras -> return@forEach

                // At the end of each day our system lowers the quality by one.
                // Once the sell by date has passed, Quality degrades twice as fast.
                ItemType.Normal -> {
                    when {
                        item.sellIn > 0 -> item.quality--
                        else -> item.quality = item.quality - 2
                    }
                }

                // "Conjured" items degrade in Quality twice as fast as normal items.
                ItemType.Conjured -> {
                    when {
                        item.sellIn > 0 -> item.quality = item.quality - 2
                        else -> item.quality = item.quality - 4
                    }
                }

                // "Aged Brie" actually increases in Quality the older it gets.
                ItemType.AgedBrie -> {
                    when {
                        item.sellIn > 0 -> item.quality++
                        else -> item.quality = item.quality + 2 // note: this is not stated in the specification
                    }
                }

                // "Backstage passes", like aged brie, increases in Quality as its SellIn value decreases;
                // Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less
                // but Quality drops to 0 after the concert.
                ItemType.BackstagePass -> {
                    when {
                        item.sellIn > 10 -> item.quality++
                        item.sellIn > 5 -> item.quality = item.quality + 2
                        item.sellIn > 0 -> item.quality = item.quality + 3
                        else -> item.quality = 0
                    }
                }
            }

            // The Quality of an item is never negative and never more than 50.
            item.quality = item.quality.coerceIn(0, 50)

            // Decrease the sell by date.
            item.sellIn--
        }
    }
}

