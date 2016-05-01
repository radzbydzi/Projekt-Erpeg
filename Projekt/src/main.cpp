#include "Game.h"

sf::Mutex mutex;

int main()
{
    Game* game = new Game();
    game->start();
    delete game;
    return 0;
}
