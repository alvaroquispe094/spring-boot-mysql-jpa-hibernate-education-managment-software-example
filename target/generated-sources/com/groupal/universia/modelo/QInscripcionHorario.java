package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInscripcionHorario is a Querydsl query type for InscripcionHorario
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInscripcionHorario extends EntityPathBase<InscripcionHorario> {

    private static final long serialVersionUID = -696701542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInscripcionHorario inscripcionHorario = new QInscripcionHorario("inscripcionHorario");

    public final BooleanPath activo = createBoolean("activo");

    public final QAula aula;

    public final DateTimePath<java.util.Date> hora_fin = createDateTime("hora_fin", java.util.Date.class);

    public final DateTimePath<java.util.Date> hora_inicio = createDateTime("hora_inicio", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInscripcion inscripcion;

    public final QSemana semana;

    public QInscripcionHorario(String variable) {
        this(InscripcionHorario.class, forVariable(variable), INITS);
    }

    public QInscripcionHorario(Path<? extends InscripcionHorario> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInscripcionHorario(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInscripcionHorario(PathMetadata metadata, PathInits inits) {
        this(InscripcionHorario.class, metadata, inits);
    }

    public QInscripcionHorario(Class<? extends InscripcionHorario> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.aula = inits.isInitialized("aula") ? new QAula(forProperty("aula")) : null;
        this.inscripcion = inits.isInitialized("inscripcion") ? new QInscripcion(forProperty("inscripcion"), inits.get("inscripcion")) : null;
        this.semana = inits.isInitialized("semana") ? new QSemana(forProperty("semana")) : null;
    }

}

