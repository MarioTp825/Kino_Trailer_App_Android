package com.tepe.mymovie.ui.dashboard

class MineSweeper(board: String, nMines: Int) {
    private val intBoard = board.split('|').map { row ->
        row.replace("\n","").split(' ').map { cell ->
            if (cell == "x") {
                -1
            } else {
                cell.toInt()
            }
        }
    }

    private val displayBoard: List<MutableList<Char>> = board.split('|').map { row ->
        row.replace("\n","").split(' ').map { cell ->
            if (cell == "0") {
                '0'
            } else {
                '?'
            }
        }.toMutableList()
    }

    fun solve(): String {
        // Your code here...
        return ""
    }
}