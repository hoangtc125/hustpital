package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhongKhamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhongKham.class);
        PhongKham phongKham1 = new PhongKham();
        phongKham1.setId("id1");
        PhongKham phongKham2 = new PhongKham();
        phongKham2.setId(phongKham1.getId());
        assertThat(phongKham1).isEqualTo(phongKham2);
        phongKham2.setId("id2");
        assertThat(phongKham1).isNotEqualTo(phongKham2);
        phongKham1.setId(null);
        assertThat(phongKham1).isNotEqualTo(phongKham2);
    }
}
