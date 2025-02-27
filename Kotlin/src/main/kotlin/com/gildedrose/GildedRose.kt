package com.gildedrose

class GildedRose(var items: Array<Item>) {

    /**
     * Invoked at the end of each day. Lowers the sellIn property of all items and updates the quality as needed.
     */
    fun updateQuality() {
        items.forEach { item ->
            // "Sulfuras", being a legendary item, never decreases in Quality and never has to be sold.
            if (item.name == "Sulfuras, Hand of Ragnaros") {
                return
            }

            // Increase the Quality of "Aged Brie" and "Backstage passes" and decrease the Quality of other items.
            if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
                // The Quality of an item is never negative.
                if (item.quality > 0) {
                    item.quality--
                }

                // "Conjured" items degrade in Quality twice as fast as normal items
                if (item.name.startsWith("Conjured ") && item.quality > 0) {
                    item.quality--
                }
            } else {
                // The Quality of "Aged Brie" and "Backstage passes" is never above 50.
                if (item.quality < 50) {
                    item.quality++

                    if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                        // Quality of "Backstage passes" increases by 2 when there are 10 days or less remaining.
                        if (item.sellIn < 11 && item.quality < 50) {
                            item.quality++
                        }

                        // Quality of "Backstage passes" increases by 3 when there are 5 days or less remaining.
                        if (item.sellIn < 6 && item.quality < 50) {
                            item.quality++
                        }
                    }
                }
            }

            // Decrease the sell by date.
            item.sellIn--

            // Once the sell by date has passed, Quality decreases or increases twice as fast.
            if (item.sellIn < 0) {
                if (item.name != "Aged Brie") {
                    if (item.name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.quality > 0) {
                            item.quality--
                        }

                        // "Conjured" items degrade in Quality twice as fast as normal items
                        if (item.name.startsWith("Conjured ") && item.quality > 0) {
                            item.quality--
                        }
                    } else {
                        // Quality of "Backstage passes" drops to 0 after the concert
                        item.quality = 0
                    }
                } else {
                    if (item.quality < 50) {
                        item.quality++
                    }
                }
            }
        }
    }
}

