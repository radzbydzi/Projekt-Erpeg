#include "game.h"
sf::Mutex mutex;

int main()
{
    GameObject* game = new GameObject();
    game->start();
    delete game;
    return 0;
}
