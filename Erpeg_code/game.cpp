#include "game.h"
void GameObject::start()
{
    /*Tworzymy dwa w¹tki, jeden logiczny(ca³a mechanika gry),
    drugi obs³uguj¹cy wyœwietlanie ca³ej grafiki, zdarzenia
    klikniêcia myszki czy klawiatury
    */
    window.create(sf::VideoMode(800, 600), "My window");//przypisuje oknu właściwości, czyli jakby je tworzy,
                                                        //musi on być tu bo bogowie informatyki nie byli przychylni
    graphicalthread.launch(); // uruchomienie w¹tku
    logicalthread.launch();
    interactionThreadFunc();//interakcja z użytkownikiem w petli głownej
}
GameObject::~GameObject()
{
}
void GameObject::graphicsThreadFunc()
{
    cout<<"Jestem w¹tkiem graficznym"<<endl;
    sf::CircleShape shape(100.f);
    shape.setFillColor(sf::Color::Blue);
window.draw(shape);
        window.display();
    while (window.isOpen())
    {

    }
}

void GameObject::logicThreadFunc()
{
    cout<<"Jestem w¹tkiem logicznym"<<endl;
}

void GameObject::interactionThreadFunc()
{
    cout<<"Jestem w¹tkiem interakcji"<<endl;
    //sf::RenderWindow window(sf::VideoMode(200, 200), "SFML works!");
    window.setActive(false);
    while (window.isOpen())
    {
        sf::Event event;
        while (window.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                window.close();
            else if(event.key.code == sf::Keyboard::W)
            {
                cout<<"w"<<endl;
            }
        }
    }
}
