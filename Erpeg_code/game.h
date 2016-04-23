#include <SFML/Graphics.hpp>
#include <iostream>

using namespace std;
class GameObject
{
public:
    ~GameObject(); //deklaracja dekonstruktora

    GameObject()
        : graphicalthread(&GameObject::graphicsThreadFunc, this),//deklaracja konstruktora
        logicalthread(&GameObject::logicThreadFunc, this){};
    void start();
private:
    sf::Thread graphicalthread;//deklaracja w�tku z przypisaniem do adresu metody naszej klasy
    sf::Thread logicalthread;
    void graphicsThreadFunc();//deklaracja metody zawierajacej to co ma by� wykonywane w w�tku graficznym
    void logicThreadFunc();//deklaracja metody zawierajacej to co ma by� wykonywane w w�tku logicznym
};
