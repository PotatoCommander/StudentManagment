package Model.Abstraction;

import Model.Message;

public interface Observer
{
    void Update(Message message);
}
