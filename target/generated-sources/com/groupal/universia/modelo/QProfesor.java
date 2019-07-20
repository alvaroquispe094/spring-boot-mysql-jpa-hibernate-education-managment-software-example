package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProfesor is a Querydsl query type for Profesor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProfesor extends EntityPathBase<Profesor> {

    private static final long serialVersionUID = 797367979L;

    public static final QProfesor profesor = new QProfesor("profesor");

    public final BooleanPath activo = createBoolean("activo");

    public final StringPath apellido = createString("apellido");

    public final StringPath direccion = createString("direccion");

    public final NumberPath<Integer> documento = createNumber("documento", Integer.class);

    public final DateTimePath<java.util.Date> fecha = createDateTime("fecha", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath mail = createString("mail");

    public final StringPath nombre = createString("nombre");

    public final StringPath password = createString("password");

    public final StringPath telefono = createString("telefono");

    public final StringPath titulo = createString("titulo");

    public final StringPath username = createString("username");

    public QProfesor(String variable) {
        super(Profesor.class, forVariable(variable));
    }

    public QProfesor(Path<? extends Profesor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfesor(PathMetadata metadata) {
        super(Profesor.class, metadata);
    }

}

