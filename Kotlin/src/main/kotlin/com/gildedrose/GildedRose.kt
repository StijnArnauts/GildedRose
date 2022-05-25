package com.gildedrose

class GildedRose(var items: Array<Item>) {

    /**
     * Invoked at the end of each day. Lowers the sellIn property of all items and updates the quality as needed.
     */
    fun updateQuality() {
        for (i in items.indices) {
            // Increase the Quality of "Aged Brie" and "Backstage passes" and decrease the Quality of other items.
            // "Sulfuras", being a legendary item, never decreases in Quality.
            if (items[i].name != "Aged Brie" && items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                // The Quality of an item is never negative.
                if (items[i].quality > 0) {
                    if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                        items[i].quality = items[i].quality - 1
                    }
                }
            } else {
                // The Quality of "Aged Brie" and "Backstage passes" is never above 50.
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1

                    if (items[i].name == "Backstage passes to a TAFKAL80ETC concert") {
                        // Quality of "Backstage passes" increases by 2 when there are 10 days or less remaining.
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }

                        // Quality of "Backstage passes" increases by 3 when there are 5 days or less remaining.
                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }
                    }
                }
            }

            // Decrease the sell by date. "Sulfuras", being a legendary item, never has to be sold.
            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                items[i].sellIn = items[i].sellIn - 1
            }

            // Once the sell by date has passed, Quality decreases or increases twice as fast.
            if (items[i].sellIn < 0) {
                if (items[i].name != "Aged Brie") {
                    if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (items[i].quality > 0) {
                            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                                items[i].quality = items[i].quality - 1
                            }
                        }
                    } else {
                        // Quality of "Backstage passes" drops to 0 after the concert
                        items[i].quality = items[i].quality - items[i].quality
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1
                    }
                }
            }
        }
    }

}

