package model.abstractions;

import model.Message;

public interface Observer
{
    void Update(Message message);
}
