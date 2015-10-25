package com.writeoncereadmany.util;

/**
 * Container for a value which may be set at some point in the future.
 * Much simpler, but also weaker, than a promise.
 */
public class Hope<T>
{
    private T value = null;

    public void set(T value)
    {
        if(this.value == null)
        {
            this.value = value;
        }
        // permit re-resolution to same value, because our use-case re-uses the same damn terminal :(
        // this is because of how binary operators work. i don't know if it's bad or not?
        else if (!this.value.equals(value))
        {
            throw new UnsupportedOperationException("Value has already been set");
        }
    }

    public T get()
    {
        if(this.value == null)
        {
            throw new UnsupportedOperationException("Value has not been set yet");
        }
        return this.value;
    }
}
