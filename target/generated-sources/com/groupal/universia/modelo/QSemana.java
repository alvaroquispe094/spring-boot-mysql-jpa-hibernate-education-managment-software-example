package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSemana is a Querydsl query type for Semana
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSemana extends EntityPathBase<Semana> {

    private static final long serialVersionUID = -1485127526L;

    public static final QSemana semana = new QSemana("semana");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nombre = createString("nombre");

    public QSemana(String variable) {
        super(Semana.class, forVariable(variable));
    }

    public QSemana(Path<? extends Semana> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSemana(PathMetadata metadata) {
        super(Semana.class, metadata);
    }

}

