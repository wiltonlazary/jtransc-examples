package example




object Bot {
	fun playTurn(game: Model.Game, chip: ChipType) {
		val cell = game.getEmptyCells().random()
		game[cell.x, cell.y] = chip
	}
}

class IngameScene(val ingameView: Views.Ingame) {
	val game = Model.Game()

	fun start() {
		game.onCellUpdated.add { cell ->
			ingameView.cell(cell.x, cell.y).put(cell.value)
		}

		game.onGameFinished.add { result ->
			println(result)
			game.reset()
		}

		Array2.forEach(3, 3) { x, y ->
			val cell = ingameView.cells[y][x]

			cell.mouse.onHover.add { cell.hover() }
			cell.mouse.onOut.add { cell.out() }
			cell.mouse.onClick.add {
				if (game.isEmpty(x, y)) {
					game[x, y] = ChipType.O

					Bot.playTurn(game, ChipType.X)
				}
			}
		}
	}
}
