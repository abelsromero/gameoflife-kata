package org.abelsromero.kata.gameoflife

import java.util.*

class GameBoard(width: Int = 0, height: Int = 0) {

    private val width = width
    private val height = height
    private var cells: Array<Array<Cell>> = initCells(width, height)

    // WTF: need to trick the compiler to accept any instance of the sealed class or else refuses to compile
    private fun initCells(width: Int, height: Int) =
            Array(width) { Array(height) { if (false) AliveCell else DeadCell } }


    companion object {

        fun randomBoard(height: Int, width: Int, cells: Int): GameBoard {
            val rand = Random()
            val board =  GameBoard(height,width)

            var missing = cells
            while (missing > 0) {
                val x = rand.nextInt(width)
                val y = rand.nextInt(height)
                if (board.cells[x][y] == DeadCell) {
                    board.cells[x][y] = AliveCell
                    missing--
                }
            }

            return board
        }

    }

    override fun toString(): String {

        val bf = StringBuffer()
        for (j in 0..height - 1) {
            for (i in 0..width - 1) {
                bf.append(cells[i][j].toString())
            }
            bf.append("\n")
        }
        return bf.toString().removeSuffix("\n")
    }

    fun addCell(cell: Cell, x: Int, y: Int) {
        cells[x][y] = cell
    }

    fun iterate() {
        val newCells = initCells(width, height)

        for (j in 0..height - 1) {
            for (i in 0..width - 1) {
                val neighbours = neighbours(i, j)
                newCells[i][j] =
                        when (cells[i][j]) {
                            is AliveCell -> {
                                when (neighbours - 1) {
                                    0, 1 -> DeadCell
                                    2, 3 -> AliveCell
                                    else -> DeadCell
                                }
                            }
                            is DeadCell -> {
                                when (neighbours) {
                                    3 -> AliveCell
                                    else -> DeadCell
                                }
                            }
                        }
            }
        }
        cells = newCells
    }

    private fun neighbours(x: Int, y: Int): Int {
        var counter = 0
        for (i in xRange(x)) {
            for (j in yRange(y)) {
                if (cells[i][j] == AliveCell)
                    counter++
            }
        }
        return counter
    }


    private fun xRange(x: Int) = when (x) {
        0 -> 0..if (width > 1) 1 else width
        width - 1 -> x - 1..x
        else -> x - 1..x + 1
    }

    private fun yRange(y: Int) = when (y) {
        0 -> 0..if (height > 1) 1 else height
        height - 1 -> y - 1..y
        else -> y - 1..y + 1
    }

}
