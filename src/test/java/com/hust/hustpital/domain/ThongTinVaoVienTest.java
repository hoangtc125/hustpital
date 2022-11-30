package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThongTinVaoVienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThongTinVaoVien.class);
        ThongTinVaoVien thongTinVaoVien1 = new ThongTinVaoVien();
        thongTinVaoVien1.setId("id1");
        ThongTinVaoVien thongTinVaoVien2 = new ThongTinVaoVien();
        thongTinVaoVien2.setId(thongTinVaoVien1.getId());
        assertThat(thongTinVaoVien1).isEqualTo(thongTinVaoVien2);
        thongTinVaoVien2.setId("id2");
        assertThat(thongTinVaoVien1).isNotEqualTo(thongTinVaoVien2);
        thongTinVaoVien1.setId(null);
        assertThat(thongTinVaoVien1).isNotEqualTo(thongTinVaoVien2);
    }
}
