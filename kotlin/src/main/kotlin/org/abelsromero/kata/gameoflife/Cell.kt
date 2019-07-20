package org.abelsromero.kata.gameoflife

sealed class Cell

object DeadCell : Cell() {
    override fun toString() = "x"
}

object AliveCell : Cell() {
    override fun toString() = "o"
}
