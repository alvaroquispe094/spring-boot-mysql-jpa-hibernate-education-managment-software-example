package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInscripcion is a Querydsl query type for Inscripcion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInscripcion extends EntityPathBase<Inscripcion> {

    private static final long serialVersionUID = -1801251544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInscripcion inscripcion = new QInscripcion("inscripcion");

    public final BooleanPath activo = createBoolean("activo");

    public final NumberPath<Integer> cantidad = createNumber("cantidad", Integer.class);

    public final QCarrera carrera;

    public final QEstado estado;

    public final DateTimePath<java.util.Date> fecha_fin = createDateTime("fecha_fin", java.util.Date.class);

    public final DateTimePath<java.util.Date> fecha_inicio = createDateTime("fecha_inicio", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMateria materia;

    public QInscripcion(String variable) {
        this(Inscripcion.class, forVariable(variable), INITS);
    }

    public QInscripcion(Path<? extends Inscripcion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInscripcion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInscripcion(PathMetadata metadata, PathInits inits) {
        this(Inscripcion.class, metadata, inits);
    }

    public QInscripcion(Class<? extends Inscripcion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carrera = inits.isInitialized("carrera") ? new QCarrera(forProperty("carrera")) : null;
        this.estado = inits.isInitialized("estado") ? new QEstado(forProperty("estado")) : null;
        this.materia = inits.isInitialized("materia") ? new QMateria(forProperty("materia")) : null;
    }

}

