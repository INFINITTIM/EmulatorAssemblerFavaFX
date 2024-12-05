package org.example.eajfx;

public class DAOBuilder {
    static DAO_Memory dao = new DAO_DB();
    static DAO_Memory getDAO() {
        return dao;
    }
}
