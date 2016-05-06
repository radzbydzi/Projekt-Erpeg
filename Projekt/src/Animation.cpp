#include "Animation.h"
#include<windows.h>//chwilowe
Animation::Animation()
{
    //ctor
}
Animation::Animation(string name)
{
    this->name=name;
}
Animation::Animation(string name, bool repeat)
{
    this->name=name;
    this->repeat=repeat;
}
Animation::~Animation()
{
    for(int i = 0; i<frames.size();i++)
        frames.pop_back();
}
void Animation::addFrame(int x, int y, int w, int h)
{
    frames.push_back({x,y,w,h});
}
void Animation::removeFrame()
{
    cout<<"tu ma usuwac klatke animacji; nie wiem po co ale niech bedzie"<<endl;
}
frame Animation::getFrame(int nr)
{
    return frames[nr];
}
void Animation::setCurrentFrame(int nr)
{
    currentFrame=nr;
}
void Animation::setNextFrameAsCurrent()
{
    if((currentFrame+1)<frames.size())
    {
        currentFrame+=1;
    }
    else
    {
        if(repeat==true)
        {
            currentFrame=0;
        }
    }
}
void Animation::setPreviousFrameAsCurrent()
{
    if((currentFrame-1)>frames.size())
    {
        currentFrame-=1;
    }
    else
    {
        if(repeat==true)
        {
            currentFrame=frames.size()-1;//bo jesli tablica jest 5 elementowa to jest z zakresu 0-4
        }
    }
}
void Animation::setTexture(sf::Texture* texture)
{
    this->texture=texture;
}
sf::Sprite Animation::getSprite()
{
    sf::Sprite sprite;
    sprite.setTexture(*texture);
    frame tmpFrHldr=getFrame(currentFrame);//tymczasowy trzymacz aktualnej klatki
    //cout<<": "<<this<<endl;

    sprite.setTextureRect(sf::IntRect(tmpFrHldr.x, tmpFrHldr.y, tmpFrHldr.w, tmpFrHldr.h));
    return sprite;
}
void Animation::resetAnimation()
{
    setCurrentFrame(0);
}
string Animation::getName()
{
    return name;
}

void Animation::setRepeat(bool repeat)
{
    this->repeat=repeat;
}
