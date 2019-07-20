package org.ab

import org.abelsromero.kata.gameoflife.AliveCell
import org.abelsromero.kata.gameoflife.DeadCell
import org.abelsromero.kata.gameoflife.GameBoard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class GameBoardTest {

    @Test
    fun should_create_empty_board() {
        // when
        val board = GameBoard()
        // then
        assertThat(board).isNotNull
    }

    @Test
    fun should_create_evenly_sized_board() {
        // when
        val board = GameBoard(4, 4)
        // then
        assertThat(board).isNotNull
        assertThat(board.toString()).isEqualTo("""
            xxxx
            xxxx
            xxxx
            xxxx
            """.trimIndent())
    }

    @Test
    fun should_create_unevenly_sized_board() {
        // when
        val board = GameBoard(2, 4)
        // then
        assertThat(board).isNotNull
        assertThat(board.toString()).isEqualTo("""
            xx
            xx
            xx
            xx
            """.trimIndent())
    }

    @Test
    fun should_place_an_alive_cell_in_board() {
        // when
        val board = GameBoard(2, 4)
        board.addCell(AliveCell, 1, 2)
        // then
        assertThat(board).isNotNull
        assertThat(board.toString()).isEqualTo("""
            xx
            xx
            xo
            xx
            """.trimIndent())
    }

    @Test
    fun should_place_a_dead_cell_in_board() {
        // when
        val board = GameBoard(2, 4)
        board.addCell(AliveCell, 0, 0)
        board.addCell(AliveCell, 1, 2)
        board.addCell(AliveCell, 0, 1)
        // then
        assertThat(board.toString()).isEqualTo("""
            ox
            ox
            xo
            xx
            """.trimIndent())
        // and
        // when
        board.addCell(DeadCell, 0, 1)
        assertThat(board.toString()).isEqualTo("""
            ox
            xx
            xo
            xx
            """.trimIndent())
    }

    @Test
    fun should_not_spawn_cells_on_dead_board() {
        // given
        val board = GameBoard(3, 3)
        // when
        board.iterate()
        // then
        assertThat(board.toString()).isEqualTo("""
            xxx
            xxx
            xxx
            """.trimIndent())
    }

    @Test
    fun should_kill_cell_with_2_neighbours() {
        // given
        val board = GameBoard(3, 3)
        board.addCell(AliveCell, 1, 1)
        // when
        board.iterate()
        // then
        assertThat(board.toString()).isEqualTo("""
            xxx
            xxx
            xxx
            """.trimIndent())
    }

    @Test
    fun should_spawn_cell_with_3_neighbours() {
        // given
        val board = GameBoard(3, 3)
        board.addCell(AliveCell, 0, 1)
        board.addCell(AliveCell, 1, 0)
        board.addCell(AliveCell, 1, 2)
        assertThat(board.toString()).isEqualTo("""
            xox
            oxx
            xox
            """.trimIndent())
        // when
        board.iterate()
        // then
        assertThat(board.toString()).isEqualTo("""
            xxx
            oox
            xxx
            """.trimIndent())
    }

    @Test
    fun should_create_random_board() {
        // when
        val board = GameBoard.randomBoard(10, 10, 5)
        // then
        assertThat(board.toString().count { it.equals('o') }).isEqualTo(5)
    }

}