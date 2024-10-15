package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.pj05.model.entity.Size;
import ra.pj05.repository.ISizeRepository;
import ra.pj05.service.ISizeService;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements ISizeService {

    @Autowired
    private ISizeRepository sizeRepository;

    @Override
    public List<Size> findAllSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public Size addSize(String sizeName) {
        Size size = new Size();
        size.setSizeName(sizeName);
        return sizeRepository.save(size);
    }

    @Override
    public Size saveSize(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Size findSizeById(Long sizeId) {
        Optional<Size> size = sizeRepository.findById(sizeId);
        return size.orElseThrow(() -> new RuntimeException("Size not found"));
    }

    @Override
    public void deleteSizeById(Long sizeId) {
        sizeRepository.deleteById(sizeId);
    }

}
