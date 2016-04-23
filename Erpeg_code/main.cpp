#include "game.h"
sf::Mutex mutex;

int main()
{
    GameObject* game = new GameObject();
    game->start();
    while(1)
        cout<<"asdas"<<endl;

    return 0;
}
