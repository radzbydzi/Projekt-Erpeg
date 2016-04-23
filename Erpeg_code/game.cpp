#include "game.h"
void GameObject::start()
{
    /*Tworzymy dwa w�tki, jeden logiczny(ca�a mechanika gry),
    drugi obs�uguj�cy wy�wietlanie ca�ej grafiki, zdarzenia
    klikni�cia myszki czy klawiatury
    */
    graphicalthread.launch(); // uruchomienie w�tku
    logicalthread.launch();
}

void GameObject::graphicsThreadFunc()
{
    cout<<"Jestem w�tkiem graficznym"<<endl;
    sf::RenderWindow window(sf::VideoMode(200, 200), "SFML works!");
    sf::CircleShape shape(100.f);
    shape.setFillColor(sf::Color::Blue);

    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        window.clear();
        window.draw(shape);
        window.display();
    }

}

void GameObject::logicThreadFunc()
{
    cout<<"Jestem w�tkiem graficznym"<<endl;
}
