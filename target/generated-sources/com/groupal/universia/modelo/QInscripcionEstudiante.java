package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInscripcionEstudiante is a Querydsl query type for InscripcionEstudiante
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInscripcionEstudiante extends EntityPathBase<InscripcionEstudiante> {

    private static final long serialVersionUID = -844264166L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInscripcionEstudiante inscripcionEstudiante = new QInscripcionEstudiante("inscripcionEstudiante");

    public final BooleanPath activo = createBoolean("activo");

    public final QEstado estado;

    public final QEstudiante estudiante;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInscripcion inscripcion;

    public QInscripcionEstudiante(String variable) {
        this(InscripcionEstudiante.class, forVariable(variable), INITS);
    }

    public QInscripcionEstudiante(Path<? extends InscripcionEstudiante> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInscripcionEstudiante(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInscripcionEstudiante(PathMetadata metadata, PathInits inits) {
        this(InscripcionEstudiante.class, metadata, inits);
    }

    public QInscripcionEstudiante(Class<? extends InscripcionEstudiante> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.estado = inits.isInitialized("estado") ? new QEstado(forProperty("estado")) : null;
        this.estudiante = inits.isInitialized("estudiante") ? new QEstudiante(forProperty("estudiante"), inits.get("estudiante")) : null;
        this.inscripcion = inits.isInitialized("inscripcion") ? new QInscripcion(forProperty("inscripcion"), inits.get("inscripcion")) : null;
    }

}

