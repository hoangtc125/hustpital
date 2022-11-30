package com.hust.hustpital.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.hustpital.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChuyenKhoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChuyenKhoa.class);
        ChuyenKhoa chuyenKhoa1 = new ChuyenKhoa();
        chuyenKhoa1.setId("id1");
        ChuyenKhoa chuyenKhoa2 = new ChuyenKhoa();
        chuyenKhoa2.setId(chuyenKhoa1.getId());
        assertThat(chuyenKhoa1).isEqualTo(chuyenKhoa2);
        chuyenKhoa2.setId("id2");
        assertThat(chuyenKhoa1).isNotEqualTo(chuyenKhoa2);
        chuyenKhoa1.setId(null);
        assertThat(chuyenKhoa1).isNotEqualTo(chuyenKhoa2);
    }
}
