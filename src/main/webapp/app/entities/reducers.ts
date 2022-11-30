import phongKham from 'app/entities/phong-kham/phong-kham.reducer';
import chuyenKhoa from 'app/entities/chuyen-khoa/chuyen-khoa.reducer';
import countries from 'app/entities/countries/countries.reducer';
import cities from 'app/entities/cities/cities.reducer';
import districts from 'app/entities/districts/districts.reducer';
import wards from 'app/entities/wards/wards.reducer';
import ethnics from 'app/entities/ethnics/ethnics.reducer';
import jobs from 'app/entities/jobs/jobs.reducer';
import bhyt from 'app/entities/bhyt/bhyt.reducer';
import patients from 'app/entities/patients/patients.reducer';
import thongTinVaoVien from 'app/entities/thong-tin-vao-vien/thong-tin-vao-vien.reducer';
import banks from 'app/entities/banks/banks.reducer';
import lichLamViec from 'app/entities/lich-lam-viec/lich-lam-viec.reducer';
import doctors from 'app/entities/doctors/doctors.reducer';
import lichHen from 'app/entities/lich-hen/lich-hen.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  phongKham,
  chuyenKhoa,
  countries,
  cities,
  districts,
  wards,
  ethnics,
  jobs,
  bhyt,
  patients,
  thongTinVaoVien,
  banks,
  lichLamViec,
  doctors,
  lichHen,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
