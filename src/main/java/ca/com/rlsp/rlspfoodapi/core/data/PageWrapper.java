package ca.com.rlsp.rlspfoodapi.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*
    Faz a conversao (volta para o original) de PAGE (da paginacao) acertar a paginacao ,ja que o translatePageable (em OrderController) muda a
    referencia dos nomes do atributos da classe ao paginar
 */
public class PageWrapper<T> extends PageImpl<T> {

    private Pageable pageable;

    public PageWrapper(Page<T> page, Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());

        this.pageable = pageable;
    }


    @Override
    public Pageable getPageable() {
        return this.pageable;
    }
}
