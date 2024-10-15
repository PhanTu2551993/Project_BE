package ra.pj05.service;

import ra.pj05.model.entity.Size;

import java.util.List;

public interface ISizeService {
    List<Size> findAllSizes();
    Size addSize(String sizeName);
    Size saveSize(Size size);
    Size findSizeById(Long sizeId);
    void deleteSizeById(Long sizeId);
}
