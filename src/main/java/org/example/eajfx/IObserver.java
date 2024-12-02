package org.example.eajfx;

public interface IObserver {
    void event(ProgramModel p);
    void event(CPUModel cpu);
    void event(ExecuterModel ex);
}
