#include "Object.h"
std::vector<std::string> split(const std::string &text, char sep) {
  std::vector<std::string> tokens;
  std::size_t start = 0, end = 0;
  while ((end = text.find(sep, start)) != std::string::npos) {
    tokens.push_back(text.substr(start, end - start));
    start = end + 1;
  }
  tokens.push_back(text.substr(start));
  return tokens;
}
struct Tmp
{
    Tmp(Animation* a, void* b)
    {
        an_adr=a;
        adr=b;
    }
    Animation* an_adr;
    void* adr;
};
vector<Tmp> adr;
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
void Object::addAnimation(Animation anim)
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
        if(animations[i].getName()==name)
        {
            setCurrentAnimation(i);
            break;
        }
    }
}
Animation* Object::getCurrentAnimation()
{
    if(adr.size()==0)
    {
        adr.push_back(Tmp(&animations[currentAnimation],this));
    }else
    {
        bool abc=true;
        for(int i=0; i<adr.size(); i++)
        {
            if(adr[i].an_adr==&animations[currentAnimation] && adr[i].adr==this)
            {
                abc=false;
                break;
            }
        }
        if(abc==true)
        {
            adr.push_back(Tmp(&animations[currentAnimation],this));
            cout<<"adr "<<&animations[currentAnimation]<<" "<<this<<endl;
        }
    }


    return &animations[currentAnimation];
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
string Object::getName()
{
    return name;
}
void Object::processNextTask()
{
    for(int i=0; i<longTermTaskQueue.size(); i++)
    {
        vector<string> splited = split(longTermTaskQueue[i].command,':');
        if(splited[0]=="animchg")
        {
            if(getCurrentAnimation()->getName()!=splited[1])
            {
                setCurrentAnimation(splited[1]);
                getCurrentAnimation()->resetAnimation();
            }else
            {
                 getCurrentAnimation()->setNextFrameAsCurrent();
            }
        }
    }
    if(taskQueue.size()>0)
        {
            //parse taskQueue

            vector<string> splited = split(taskQueue[0].command,':');
            if(splited[0]=="go")
            {
                //cout<<"rot: "<<tmpobj->getRotation()<<endl;
                if(splited[2]=="X")
                {

                    setX(getX()+atoi( splited[1].c_str()));

                    //cout<<"go: "<<tmpobj->getX()<<" : "<< splited[2]<<endl;
                }else if(splited[2]=="Y")
                {
                    setY(getY()+atoi( splited[1].c_str()));

                }
            }else if(splited[0]=="animchg")
            {
                if(getCurrentAnimation()->getName()!=splited[1])
                {

                    setCurrentAnimation(splited[1]);
                    getCurrentAnimation()->resetAnimation();
                }else
                {
                    getCurrentAnimation()->setNextFrameAsCurrent();
                }

            }
            taskQueue.pop_front();
        }
}
void Object::addTask(ThingsToDo ttd)
{
    taskQueue.push_back(ttd);
}
void Object::delFrontTask()
{
    taskQueue.pop_front();
}
void Object::delBackTask()
{
    taskQueue.pop_back();
}
void Object::delAllTaskWithCommand(string command)
{
    for(int i=0; i<taskQueue.size(); i++)
    {
        vector<string> spl_com=split(taskQueue[i].command, ':');
        if(spl_com[0]==command)
        {
            taskQueue.erase(taskQueue.begin()+i-1);
        }
    }
}
//------
void Object::addLongTermTask(ThingsToDo ttd)
{
    longTermTaskQueue.push_back(ttd);
}
void Object::delFrontLongTermTask()
{
    longTermTaskQueue.pop_front();
}
void Object::delBackLongTermTask()
{
    longTermTaskQueue.pop_back();
}
void Object::delAllLongTermTaskWithCommand(string command)
{
    for(int i=0; i<longTermTaskQueue.size(); i++)
    {
        vector<string> spl_com=split(longTermTaskQueue[i].command, ':');
        if(spl_com[0]==command)
        {
            longTermTaskQueue.erase(longTermTaskQueue.begin()+i-1);
        }
    }
}
void Object::delLongTermTaskById(int id)
{
            longTermTaskQueue.erase(longTermTaskQueue.begin()+id-1);
}
