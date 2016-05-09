#ifndef ANIMATION_H
#define ANIMATION_H
#include <SFML/Graphics.hpp>
#include <string>
#include <deque>
#include <iostream>
using namespace std;

struct frame
{
    int x;//wspolrzedne w tilesecie
    int y;
    int w;//szerokosc
    int h;//wysokosc
    frame(int x, int y, int w, int h)
    {
        this->x=x;
        this->y=y;
        this->w=w;
        this->h=h;
    }
};
class Animation
{
    public:

        Animation();
        Animation(string name);
        Animation(string name, bool repeat);
        ~Animation();
        void addFrame(int x, int y, int w=32, int h=64);//dodaje klatke
        void removeFrame();//usuwa klatke
        frame getFrame(int nr);//pobiera kaltke w strukturze o podanym numerze
        void setCurrentFrame(int nr);//wybiera klatke o podanym numerze jako biezacy
        void setNextFrameAsCurrent();//bierze nastepna klatke jako bierzaca
        void setPreviousFrameAsCurrent();//bierze poprzednia klatke jako biezaca
        void setTexture(sf::Texture* texture);//wybiera teksture/tileset animacji
        sf::Sprite getSprite();//pobiera obiekt SFML Sprite z naszymi parametrami
        void resetAnimation();
        string getName();
        void setRepeat(bool repeat);

        void setFrameDuration(int duration);
        void setKeepPlaying(bool keepPlaying);
        int getFrameDuration();
        bool getKeepPlaying();

    protected:

    private:
        string name;//nazwa
        bool repeat; //czy animacja ma byc zapetlona; jesli tak to gdy nextFrame nie bedzie mniejsze od wielkosci listy to przechodzi do pierwszej klatki(w przypadku peviousFrame do ostatniej)
        deque<frame> frames;//lista klatek
        int currentFrame;//numer aktualnej klatki w liscie
        sf::Texture* texture;// wskaznik na teksture/ w ktorej znajduje sie ta animacja
        sf::Clock clock;
        //sf::Time time;//zmienna
        bool keepPlaying=false;//czy animacja jest odgrywana czy nie
        int frameDuration=100;//czas trwania pojedynczej klatki w milisekundach
};

#endif // ANIMATION_H
