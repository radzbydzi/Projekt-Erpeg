#include "game.h"
void GameObject::start()
{
    /*Tworzymy dwa w¹tki, jeden logiczny(ca³a mechanika gry),
    drugi obs³uguj¹cy wyœwietlanie ca³ej grafiki, zdarzenia
    klikniêcia myszki czy klawiatury
    */
    graphicalthread.launch(); // uruchomienie w¹tku
    logicalthread.launch();
}

void GameObject::graphicsThreadFunc()
{
    cout<<"Jestem w¹tkiem graficznym"<<endl;
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
    cout<<"Jestem w¹tkiem graficznym"<<endl;
}
