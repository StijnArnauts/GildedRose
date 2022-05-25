package com.gildedrose

enum class ItemType {
    Normal, Conjured, AgedBrie, BackstagePass, Sulfuras;

    companion object {
        fun getType(item: Item): ItemType = when {
            item.name == "Sulfuras, Hand of Ragnaros" -> Sulfuras
            item.name == "Aged Brie" -> AgedBrie
            item.name == "Backstage passes to a TAFKAL80ETC concert" -> BackstagePass
            item.name.startsWith("Conjured ") -> Conjured
            else -> Normal
        }
    }
}