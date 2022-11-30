package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LichLamViecTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LichLamViec.class);
        LichLamViec lichLamViec1 = new LichLamViec();
        lichLamViec1.setId("id1");
        LichLamViec lichLamViec2 = new LichLamViec();
        lichLamViec2.setId(lichLamViec1.getId());
        assertThat(lichLamViec1).isEqualTo(lichLamViec2);
        lichLamViec2.setId("id2");
        assertThat(lichLamViec1).isNotEqualTo(lichLamViec2);
        lichLamViec1.setId(null);
        assertThat(lichLamViec1).isNotEqualTo(lichLamViec2);
    }
}
