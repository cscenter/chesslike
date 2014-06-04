package AI;

import model.Game;

import model.coord.Route;

public interface AI {

	public Route getRoute(Game game);

    public boolean isAI();
	
}