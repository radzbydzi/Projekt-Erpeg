#include "ThingsToDo.h"

ThingsToDo::ThingsToDo(string command, string type, int importance)
{
    this->command=command;
    this->type=type;
    this->importance=importance;
}

ThingsToDo::~ThingsToDo()
{
}
