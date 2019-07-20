package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInscripcionEstudianteNota is a Querydsl query type for InscripcionEstudianteNota
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInscripcionEstudianteNota extends EntityPathBase<InscripcionEstudianteNota> {

    private static final long serialVersionUID = -206400472L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInscripcionEstudianteNota inscripcionEstudianteNota = new QInscripcionEstudianteNota("inscripcionEstudianteNota");

    public final BooleanPath activo = createBoolean("activo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInscripcionEstudiante inscripcionEstudiante;

    public final NumberPath<Integer> nota1 = createNumber("nota1", Integer.class);

    public final NumberPath<Integer> nota2 = createNumber("nota2", Integer.class);

    public final NumberPath<Integer> nota_final = createNumber("nota_final", Integer.class);

    public QInscripcionEstudianteNota(String variable) {
        this(InscripcionEstudianteNota.class, forVariable(variable), INITS);
    }

    public QInscripcionEstudianteNota(Path<? extends InscripcionEstudianteNota> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInscripcionEstudianteNota(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInscripcionEstudianteNota(PathMetadata metadata, PathInits inits) {
        this(InscripcionEstudianteNota.class, metadata, inits);
    }

    public QInscripcionEstudianteNota(Class<? extends InscripcionEstudianteNota> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inscripcionEstudiante = inits.isInitialized("inscripcionEstudiante") ? new QInscripcionEstudiante(forProperty("inscripcionEstudiante"), inits.get("inscripcionEstudiante")) : null;
    }

}

