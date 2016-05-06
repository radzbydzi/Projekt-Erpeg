#include "Object.h"

Object::Object()
{
    //ctor
}
Object::Object(string name)
{
    this->name=name;
}
Object::~Object()
{
    for(int i = 0; i<animations.size();i++)
        animations.pop_back();

}
void Object::addAnimation(Animation* anim)
{
    animations.push_back(anim);
}
void Object::removeAnimation()
{
    cout<<"kod usuwania animacji"<<endl;
}
void Object::setCurrentAnimation(int nr)
{
    currentAnimation=nr;
}
void Object::setCurrentAnimation(string name)
{
    for(int i=0;i<animations.size();i++)
    {
        if(animations[i]->getName()==name)
        {
            setCurrentAnimation(i);
            break;
        }
    }
}
Animation* Object::getCurrentAnimation()
{
    return animations[currentAnimation];
}
void Object::setX(int x)
{
    this->x=x;
}
void Object::setY(int y)
{
    this->y=y;
}
void Object::setXY(int x, int y)
{
    setX(x);
    setY(y);
}
int Object::getX()
{
    return x;
}
int Object::getY()
{
    return y;
}
//
void Object::setOriginX(int x)
{
    this->x=x;
}
void Object::setOriginY(int y)
{
    this->y=y;
}
void Object::setOriginXY(int x, int y)
{
    setOriginX(x);
    setOriginY(y);
}
int Object::getOriginX()
{
    return x;
}
int Object::getOriginY()
{
    return y;
}

void Object::setRotation(int rot)
{
    this->rot=rot;
}
int Object::getRotation()
{
    return rot;
}
void Object::rotateObject()
{
    //a tu nie wiem co na razie
}
void Object::setName(string name)
{
    this->name=name;
}
